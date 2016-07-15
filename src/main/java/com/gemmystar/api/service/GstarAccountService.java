package com.gemmystar.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.athena.peacock.controller.web.common.model.ExtjsGridParam;
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
public class GstarAccountService {

	@Autowired
	private GstarAccountRepository repository;
	
	public GstarAccountService() {
		// TODO Auto-generated constructor stub
	}
	
	public void insertGstarAccount(GstarAccount gstarAccount){
		repository.insertGstarAccount(gstarAccount);
	}
	
	public List<GstarAccount> getGstarAccountList(ExtjsGridParam gridParam){
		return repository.getGstarAccountList(gridParam);
	}
	
	public int getGstarAccountListTotalCount(ExtjsGridParam gridParam){
		
		return repository.getGstarAccountListTotalCount(gridParam);
	}
	
	public GstarAccount getGstarAccount(GstarAccount gstarAccount){
		return repository.getGstarAccount(gstarAccount);
	}
	
	public void updateGstarAccount(GstarAccount gstarAccount){
		repository.updateGstarAccount(gstarAccount);
	}
	
	public void deleteGstarAccount(GstarAccount gstarAccount){
		repository.deleteGstarAccount(gstarAccount);
	}

}
//end of GstarAccountService.java