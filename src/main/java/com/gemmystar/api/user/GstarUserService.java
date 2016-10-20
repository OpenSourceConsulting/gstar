package com.gemmystar.api.user;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.gemmystar.api.GemmyConstant;
import com.gemmystar.api.contents.GstarContentsService;
import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.point.GstarPointService;
import com.gemmystar.api.point.GstarUserPointService;
import com.gemmystar.api.point.domain.GstarPoint;
import com.gemmystar.api.point.domain.GstarUserPoint;
import com.gemmystar.api.user.domain.GstarAccount;
import com.gemmystar.api.user.domain.GstarUser;
import com.gemmystar.api.user.domain.GstarUserRepository;
import com.gemmystar.api.user.specs.GstarUserSpecs;

/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class GstarUserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GstarUserService.class);

	@Autowired
	private GstarUserRepository repository;
	
	@Autowired
	private GstarAccountService accountService;
	
	@Autowired
	private GstarContentsService contentsService;
	
	@Autowired
	private GstarPointService pointservice;
	
	@Autowired
	private GstarUserPointService userPointservice;
	
	public GstarUserService() {
	}
	
	@Transactional
	public void join(GstarUser gstarUser, GstarAccount account){
		
		repository.save(gstarUser);
		
		account.setGstarUser(gstarUser);
		
		accountService.save(account);
		
		/*
		 * 회원가입시 기본 무료포인트 제공
		 */
		GstarPoint gstarPoint = pointservice.getGstarPoint(GstarPointService.FREE_POINT_ID);
		GstarUserPoint gstarUserPoint = new GstarUserPoint(gstarUser.getId(), gstarPoint, GstarPointService.FREE_PG_ID);
		
		userPointservice.insertGstarUserPoint(gstarUserPoint);
		
	}
	
	@Transactional
	public void saveAdminUser(GstarUser gstarUser, GstarAccount account, String[] roles){
		
		gstarUser.setNickname(gstarUser.getName());
		account.setAccountTypeCd(GemmyConstant.CODE_ACCOUNT_TYPE_ADMIN);
		
		if (account.getId() == null || account.getId() > 0) {
			accountService.deleteRole(account.getId());
		}
		
		join(gstarUser, account);
		
		accountService.saveRole(account.getId(), roles);
	}
	
	@Transactional
	public void saveAccountRoles(Long accountId, String[] roles) {
		if (accountId == null || accountId > 0) {
			accountService.deleteRole(accountId);
		}
		
		accountService.saveRole(accountId, roles);
	}
	
	public void save(GstarUser gstarUser){
		repository.save(gstarUser);
	}
	
	@Transactional
	public void lockUser(Long gstarUserId) {
		List<GstarAccount> accounts = getGstarUser(gstarUserId).getAccounts();
		for (GstarAccount gstarAccount : accounts) {
			gstarAccount.setLocked(true);
			
			accountService.save(gstarAccount);
		}
	}
	
	@Transactional
	public void unlockUser(Long gstarUserId) {
		List<GstarAccount> accounts = getGstarUser(gstarUserId).getAccounts();
		for (GstarAccount gstarAccount : accounts) {
			gstarAccount.setLocked(false);
			
			accountService.save(gstarAccount);
		}
	}
	
	public List<GstarUser> getGstarUserAllList(){
		return repository.findAll();
	}
	
	public Page<GstarUser> getGstarMemberList(Pageable pageable, String search) {
		
		
		Specifications<GstarUser> spec = Specifications.where(GstarUserSpecs.members());
		
		if (search != null) {
			spec = spec.and(GstarUserSpecs.search(search));
		}
		
		return repository.findAll(spec, pageable);
	}
	
	public Page<GstarUser> getGstarAdminList(Pageable pageable, String search) {
		
		
		Specifications<GstarUser> spec = Specifications.where(GstarUserSpecs.admins());
		
		if (search != null) {
			spec = spec.and(GstarUserSpecs.search(search));
		}
		
		return repository.findAll(spec, pageable);
	}
	
	
	public GstarUser getGstarUser(Long userId){
		
		return repository.findOne(userId);
	}
	
	/**
	 * <pre>
	 * 사용자에 의한 회원탈퇴.
	 * </pre>
	 * @param gstarUserId
	 */
	@Transactional
	public void widthdraw(GstarUser user) {
		repository.widthdraw(user.getId());
		
		/*
		 * 탈퇴회원 컨텐츠 모두 포기상태로.
		 */
		List<GstarContents> myContents = user.getGstarContents();
		for (GstarContents gstarContents : myContents) {
			
			gstarContents.setStatusCd(GemmyConstant.CODE_CNTS_STATUS_GIVEUP);
			
			contentsService.save(gstarContents);
		}
	}
	
	@Transactional
	public void deleteAdminUser(Long userId){
		
		GstarUser dbUser = getGstarUser(userId);
		
		accountService.deleteAdminAccountByUserId(userId, dbUser.getAccounts().get(0));
		
		deleteUser(dbUser);
	}
	
	@Transactional
	public void deleteUserWithChildren(Long userId){
		
		GstarUser dbUser = getGstarUser(userId);
		
		accountService.deleteAccountByUserId(userId, dbUser.getAccounts());
		
		deleteUser(dbUser);
	}
	
	public void deleteUser(GstarUser user){
		
		try{
			repository.delete(user);
		} catch (Exception e) {
			LOGGER.error(e.toString(), e);
			
			/**
			 * update.
			 */
			GstarUser deleteUser = new GstarUser();
			
			deleteUser.setId(user.getId());
			deleteUser.setName(user.getName());
			deleteUser.setNickname(user.getNickname());
			deleteUser.setMobileNoti(false);
			deleteUser.setEmailNoti(false);
			deleteUser.setCreateDt(user.getCreateDt());
			
			repository.save(deleteUser);
		}
	}

}
//end of GstarUserService.java