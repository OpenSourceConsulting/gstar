package com.gemmystar.api.contents;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gemmystar.api.GemmyConstant;
import com.gemmystar.api.common.exception.ContentsNotFoundException;
import com.gemmystar.api.common.model.GridJsonResponse;
import com.gemmystar.api.common.model.SimpleJsonResponse;
import com.gemmystar.api.common.util.WebUtil;
import com.gemmystar.api.contents.domain.GstarContents;
import com.gemmystar.api.contents.viewmodel.ContentsPointPrice;
import com.gemmystar.api.point.GstarPointHistoryService;
import com.gemmystar.api.point.GstarPointPaymentService;
import com.gemmystar.api.point.domain.GstarPointHistory;
import com.gemmystar.api.point.domain.GstarPointPayment;
import com.gemmystar.api.user.domain.GstarAccount;
import com.gemmystar.api.user.domain.GstarUser;
import com.gemmystar.api.youtube.YoutubeService;



/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/contents")
public class GstarContentsAdminController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GstarContentsAdminController.class);
	
	@Autowired
	private GstarContentsService service;
	
	@Autowired
	private GstarPointPaymentService pointPaymentService;
	
	@Autowired
	private GstarPointHistoryService pointHistoryService;
	
	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	public GstarContentsAdminController() {
		
	}
	
	/**
	 * 
	 * @param jsonRes
	 * @param pageable
	 * @param search
	 * @return
	 */
	@RequestMapping(value="/warn/list", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getWarnList(SimpleJsonResponse jsonRes, @PageableDefault(sort = { "gstarInfo.warnCnt", "createDt" }, direction = Direction.DESC) Pageable pageable, String search){
	
		Page<GstarContents> list = service.getGstarBattleWarnContentsList(pageable, search);

		jsonRes.setData(list);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/recommands", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse recommandList(SimpleJsonResponse jsonRes){
	
		Iterable<GstarContents> list = service.getRecommandList(true);

		jsonRes.setData(list);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/{gstarContentsId}/pointprice", method = RequestMethod.GET)
	@ResponseBody
	public SimpleJsonResponse getGstarContentsPointPrice(SimpleJsonResponse jsonRes, @PathVariable("gstarContentsId") Long gstarContentsId){
	
		List<GstarPointHistory> gstarPointHistories = pointHistoryService.getGstarPointHistories(gstarContentsId, GemmyConstant.CODE_POINT_HS_STATUS_NORMAL);
		
		jsonRes.setData(new ContentsPointPrice(gstarContentsId, gstarPointHistories));
		
		return jsonRes;
	}
	
	/**
	 * <pre>
	 * gstarContentsId 컨텐츠에 대한 쨈(포인트) 계좌입금정보 저장.
	 * </pre>
	 * 
	 * @param jsonRes
	 * @param gstarContentsId
	 * @param pointPayment
	 * @return
	 */
	@RequestMapping(value="/{gstarContentsId}/payment", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse saveContentsPayment(SimpleJsonResponse jsonRes, @PathVariable("gstarContentsId") Long gstarContentsId, GstarPointPayment pointPayment){
	
		
		pointPayment.setGstarContentsId(gstarContentsId);
		
		pointPaymentService.saveAndUpdateStatus(pointPayment);
		
		return jsonRes;
	}
	
	
	@RequestMapping(value="/{gstarContentsId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleJsonResponse delete(SimpleJsonResponse jsonRes, @PathVariable("gstarContentsId") Long gstarContentsId){
		
		GstarContents contents = service.getGstarContents(gstarContentsId);
		
		service.deleteGstarContents(contents);
		
		return jsonRes;
	}
	
	@RequestMapping(value="/ordering", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse ordering(SimpleJsonResponse jsonRes, @RequestParam(name="ids") Long[] ids){
	
		service.ordering(ids);
		
		return jsonRes;
	}
	
	/**
	 * 대결 포기 하기.
	 * @param jsonRes
	 * @param gstarContentsId
	 * @param gstarRoomId
	 * @return
	 */
	@RequestMapping(value="/{gstarContentsId}/closing", method = RequestMethod.POST)
	@ResponseBody
	public SimpleJsonResponse closing(SimpleJsonResponse jsonRes, @PathVariable("gstarContentsId") Long gstarContentsId){
	
		GstarContents contents = service.getGstarContents(gstarContentsId);
		
		
		contents.setStatusCd(GemmyConstant.CODE_CNTS_STATUS_CLOSED);
		
		service.save(contents);
		
		return jsonRes;
	}
	

}
//end of GstarContentsController.java