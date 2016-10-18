package com.gemmystar.api.point;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gemmystar.api.point.domain.GstarPoint;
import com.gemmystar.api.point.domain.GstarPointRepository;


/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class GstarPointService {
	
	/** 회원가입시 기본제공 포인트ID */
	public static final Integer FREE_POINT_ID = 90;
	
	/** 회원가입시 기본제공 포인트 pgId = free */
	public static final String FREE_PG_ID = "free";

	@Autowired
	private GstarPointRepository repository;
	
	public GstarPointService() {
		
	}
	
	public void save(GstarPoint gstarPoint){
		repository.save(gstarPoint);
	}
	
	public Page<GstarPoint> getGstarPointList(Pageable pageable){
		return repository.findAll(pageable);
	}
	
	
	public GstarPoint getGstarPoint(Integer gstarPointId){
		return repository.findOne(gstarPointId);
	}
	
	public void deleteGstarPoint(Integer gstarPointId){
		repository.delete(gstarPointId);
	}

}
//end of GstarPointService.java