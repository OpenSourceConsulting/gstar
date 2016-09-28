package com.gemmystar.api.user;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.gemmystar.api.GemmyConstant;
import com.gemmystar.api.contents.GstarContentsService;
import com.gemmystar.api.contents.domain.GstarContents;
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

	@Autowired
	private GstarUserRepository repository;
	
	@Autowired
	private GstarAccountService accountService;
	
	@Autowired
	private GstarContentsService contentsService;
	
	public GstarUserService() {
	}
	
	@Transactional
	public void join(GstarUser gstarUser, GstarAccount account){
		
		repository.save(gstarUser);
		
		account.setGstarUser(gstarUser);
		
		accountService.save(account);
		
	}
	
	@Transactional
	public void saveAdminUser(GstarUser gstarUser, GstarAccount account, String[] roles){
		
		gstarUser.setNickname(gstarUser.getName());
		account.setAccountTypeCd(GemmyConstant.CODE_ACCOUNT_TYPE_ADMIN);
		
		if (account.getId() > 0) {
			accountService.deleteRole(account.getId());
		}
		
		join(gstarUser, account);
		
		accountService.saveRole(account.getId(), roles);
	}
	
	public void save(GstarUser gstarUser){
		repository.save(gstarUser);
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
	
	public void deleteGstarUser(Long userId){
		repository.delete(userId);
	}

}
//end of GstarUserService.java