package com.gemmystar.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.athena.peacock.controller.web.common.model.ExtjsGridParam;
import com.gemmystar.api.domain.CommonCode;
import com.gemmystar.api.domain.CommonCodeRepository;

/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class CommonCodeService {

	@Autowired
	private CommonCodeRepository repository;
	
	public CommonCodeService() {
		// TODO Auto-generated constructor stub
	}
	
	public void insertCommonCode(CommonCode commonCode){
		repository.insertCommonCode(commonCode);
	}
	
	public List<CommonCode> getCommonCodeList(ExtjsGridParam gridParam){
		return repository.getCommonCodeList(gridParam);
	}
	
	public int getCommonCodeListTotalCount(ExtjsGridParam gridParam){
		
		return repository.getCommonCodeListTotalCount(gridParam);
	}
	
	public CommonCode getCommonCode(CommonCode commonCode){
		return repository.getCommonCode(commonCode);
	}
	
	public void updateCommonCode(CommonCode commonCode){
		repository.updateCommonCode(commonCode);
	}
	
	public void deleteCommonCode(CommonCode commonCode){
		repository.deleteCommonCode(commonCode);
	}

}
//end of CommonCodeService.java