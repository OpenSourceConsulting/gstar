package com.gemmystar.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.athena.peacock.controller.web.common.model.ExtjsGridParam;
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
		// TODO Auto-generated constructor stub
	}
	
	public void insertGstarUser(GstarUser gstarUser){
		repository.insertGstarUser(gstarUser);
	}
	
	public List<GstarUser> getGstarUserList(ExtjsGridParam gridParam){
		return repository.getGstarUserList(gridParam);
	}
	
	public int getGstarUserListTotalCount(ExtjsGridParam gridParam){
		
		return repository.getGstarUserListTotalCount(gridParam);
	}
	
	public GstarUser getGstarUser(GstarUser gstarUser){
		return repository.getGstarUser(gstarUser);
	}
	
	public void updateGstarUser(GstarUser gstarUser){
		repository.updateGstarUser(gstarUser);
	}
	
	public void deleteGstarUser(GstarUser gstarUser){
		repository.deleteGstarUser(gstarUser);
	}

}
//end of GstarUserService.java