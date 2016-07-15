package com.gemmystar.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gemmystar.api.domain.GstarAccount;
import com.gemmystar.api.domain.GstarAccountRepository;

/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class GstarAccountService implements UserDetailsService {

	@Autowired
	private GstarAccountRepository repository;
	
	public GstarAccountService() {
		
	}
	
	public void save(GstarAccount gstarAccount){
		repository.save(gstarAccount);
	}
	
	public List<GstarAccount> getGstarAccountAllList(){
		return repository.findAll();
	}
	/*
	public int getGstarAccountListTotalCount(GridParam gridParam){
		
		return repository.getGstarAccountListTotalCount(gridParam);
	}
	*/
	
	public GstarAccount getGstarAccount(Long accountId){
		return repository.findOne(accountId);
	}

	
	public void deleteGstarAccount(Long accountId){
		repository.delete(accountId);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByLoginId(username);
	}

}
//end of GstarAccountService.java