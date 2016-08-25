package com.gemmystar.api.user;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemmystar.api.GemmyConstant;
import com.gemmystar.api.contents.GstarContentsService;
import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.user.domain.GstarAccount;
import com.gemmystar.api.user.domain.GstarUser;
import com.gemmystar.api.user.domain.GstarUserRepository;

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
	
	public void save(GstarUser gstarUser){
		repository.save(gstarUser);
	}
	
	public List<GstarUser> getGstarUserAllList(){
		return repository.findAll();
	}
	
	/*
	public int getGstarUserListTotalCount(GridParam gridParam){
		
		return repository.getGstarUserListTotalCount(gridParam);
	}
	*/
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