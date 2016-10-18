package com.gemmystar.api.point;

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
import com.gemmystar.api.point.domain.GstarPointHistory;
import com.gemmystar.api.point.domain.GstarPointPayment;
import com.gemmystar.api.point.domain.GstarPointPaymentRepository;


/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class GstarPointPaymentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GstarPointPaymentService.class);

	@Autowired
	private GstarPointPaymentRepository repository;
	
	@Autowired
	private GstarPointHistoryService pointHistoryService;
	
	public GstarPointPaymentService() {
		
	}
	
	public void save(GstarPointPayment gstarPointPayment){
		repository.save(gstarPointPayment);
	}
	
	/**
	 * <pre>
	 * 계좌입금정보 저장 & GstarPointHistory 지급완료 상태변경.
	 * </pre>
	 * @param gstarPointPayment
	 */
	@Transactional
	public void saveAndUpdateStatus(GstarPointPayment gstarPointPayment){
		repository.save(gstarPointPayment);
		
		List<GstarPointHistory> gstarPointHistories = pointHistoryService.getGstarPointHistories(gstarPointPayment.getGstarContentsId(), GemmyConstant.CODE_POINT_HS_STATUS_NORMAL);
		for (GstarPointHistory gstarPointHistory : gstarPointHistories) {
			gstarPointHistory.setStatusCd(GemmyConstant.CODE_POINT_HS_STATUS_PAID);
		}
		
		pointHistoryService.saveList(gstarPointHistories);
	}
	
	public List<GstarPointPayment> getGstarPointPaymentAllList(){
		return repository.findAll();
	}
	
	public Page<GstarPointPayment> getGstarPointPaymentList(Pageable pageable, String search){
	
		/*
		Specifications<GstarPointPayment> spec = Specifications.where(GstarPointPaymentSpecs.notBattle()).and(GstarPointPaymentSpecs.notDeteled());
		
		if (search != null) {
			spec = spec.and(GstarPointPaymentSpecs.search(search));
		}
		
		return repository.findAll(spec, pageable);
		*/
		
		return repository.findAll(pageable);
	}
	
	/*
	public int getGstarPointPaymentListTotalCount(GridParam gridParam){
		
		return repository.getGstarPointPaymentListTotalCount(gridParam);
	}
	*/
	
	public GstarPointPayment getGstarPointPayment(Integer gstarPointPaymentId){
		return repository.findOne(gstarPointPaymentId);
	}
	
	/*
	public void updateGstarPointPayment(GstarPointPayment gstarPointPayment){
		repository.updateGstarPointPayment(gstarPointPayment);
	}
	*/
	
	public void deleteGstarPointPayment(Integer gstarPointPaymentId){
		repository.delete(gstarPointPaymentId);
	}

}
//end of GstarPointPaymentService.java