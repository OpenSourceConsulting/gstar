package com.gemmystar.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gemmystar.api.domain.GstarUser;
import com.gemmystar.api.domain.GstarUserRepository;

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
	
	public GstarUserService() {
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
	
	
	public void deleteGstarUser(Long userId){
		repository.delete(userId);
	}

}
//end of GstarUserService.java