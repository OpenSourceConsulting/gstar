package com.gemmystar.api.point;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.gemmystar.api.GemmyConstant;
import com.gemmystar.api.contents.GstarContentsService;
import com.gemmystar.api.point.domain.GstarPointHistory;
import com.gemmystar.api.point.domain.GstarPointHistoryRepository;
import com.gemmystar.api.point.domain.GstarUserPoint;
import com.gemmystar.api.point.domain.GstarUserPointRepository;
import com.gemmystar.api.point.specs.GstarUserPointSpecs;


/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class GstarUserPointService {
	
	@Autowired
	private GstarUserPointRepository repository;
	
	@Autowired
	private GstarPointHistoryRepository pointHistoryRepo;
	
	@Autowired
	private GstarContentsService contentsService;
	
	public GstarUserPointService() {
		
	}
	
	public void insertGstarUserPoint(GstarUserPoint gstarUserPoint){
		repository.save(gstarUserPoint);
	}
	
	public Page<GstarUserPoint> getGstarUserPointList(Pageable pageable, Long gstarUserId, String pcStatusCd){
		
		Specifications<GstarUserPoint> spec = Specifications.where(GstarUserPointSpecs.myPonts(gstarUserId));
		
		if (StringUtils.isEmpty(pcStatusCd) == false) {
			
			spec = spec.and(GstarUserPointSpecs.eqStatus(pcStatusCd));
		}
		
		return repository.findAll(spec, pageable);
	}
	
	public Page<GstarUserPoint> getGstarUserPointList(Pageable pageable, String pcStatusCd){
		
		
		if (StringUtils.isEmpty(pcStatusCd) == false) {
			
			Specifications<GstarUserPoint> spec = Specifications.where(GstarUserPointSpecs.eqStatus(pcStatusCd));
			
			return repository.findAll(spec, pageable);
		}
		
		return repository.findAll(pageable);
	}
	
	@Transactional
	public void requestCancel(Long gstarUserPointId, Long gstarUserId, String cancelReason){
		repository.updateCancelInfo(gstarUserPointId, gstarUserId, cancelReason);
	}
	
	public void cancel(Long gstarUserPointId){
		
		GstarUserPoint userPoint = getGstarUserPoint(gstarUserPointId);
		
		userPoint.setPcStatusCd(GemmyConstant.CODE_PC_STATUS_CANCEL);
		
		repository.save(userPoint);
	}
	
	@Transactional
	public void usePoint(Long gstarUserId, Long gstarContentsId, Long[] pointIds, Integer[] points) {
		
		Assert.isTrue(pointIds.length == points.length, "different pointIds.length and points.length");
		
		Long pointSum = 0l;
		
		for (int i = 0; i < pointIds.length; i++) {
			
			Long pointId = pointIds[i];
			Integer point = points[i];
			pointSum = pointSum + point;
			
			
			GstarUserPoint userPoint = getGstarUserPoint(pointId);
			int usedPoint = userPoint.getUsedPoint() + point;
			if (usedPoint > userPoint.getPoint()) {
				throw new RuntimeException("포인트 초과 사용: 보유한 포인트보다 많은 포인트를 사용할수 없습니다. pointId="+pointId+", point=" + point);
			}
		
			pointHistoryRepo.save(new GstarPointHistory(gstarUserId, gstarContentsId, pointId, point));
		}
		
		if (pointSum > 0) {
			contentsService.increasePoint(gstarContentsId, pointSum);
		}
		
	}
	
	public GstarUserPoint getGstarUserPoint(Long gstarUserPointId){
		return repository.findOne(gstarUserPointId);
	}
	
	/*
	public void updateGstarUserPoint(GstarUserPoint gstarUserPoint){
		repository.updateGstarUserPoint(gstarUserPoint);
	}
	*/
	
	public void deleteGstarUserPoint(Long gstarUserPointId){
		repository.delete(gstarUserPointId);
	}

}
//end of GstarUserPointService.java