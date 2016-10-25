package com.gemmystar.api.board;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.gemmystar.api.board.domain.GstarBoard;
import com.gemmystar.api.board.domain.GstarBoardRepository;
import com.gemmystar.api.common.mail.MailSender;
import com.gemmystar.api.common.push.AndroidFcmSender;
import com.gemmystar.api.user.GstarUserService;
import com.gemmystar.api.user.domain.GstarUser;


/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class GstarBoardService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GstarBoardService.class);

	@Autowired
	private GstarBoardRepository repository;
	
	@Autowired
	private AndroidFcmSender fcmSender;
	
	@Autowired
	@Qualifier("gemmyMailSender")
	private MailSender mailSender;
	
	@Autowired
	private GstarUserService userService;
	
	public GstarBoardService() {
		// TODO Auto-generated constructor stub
	}
	
	public void save(GstarBoard gstarBoard){
		repository.save(gstarBoard);
	}
	
	public List<GstarBoard> getGstarBoardAllList(){
		return repository.findAll();
	}
	
	public Page<GstarBoard> getGstarBoardList(Pageable pageable, String search){
	
		/*
		Specifications<GstarBoard> spec = Specifications.where(GstarBoardSpecs.notBattle()).and(GstarBoardSpecs.notDeteled());
		
		if (search != null) {
			spec = spec.and(GstarBoardSpecs.search(search));
		}
		
		return repository.findAll(spec, pageable);
		*/
		
		return repository.findList(pageable);
	}
	
	public Page<GstarBoard> getGstarEventList(Pageable pageable, String search){
		
		
		return repository.findEventList(pageable);
	}
	
	
	public GstarBoard getGstarBoard(Integer gstarBoardId){
		return repository.findOne(gstarBoardId);
	}
	
	
	public void deleteGstarBoard(Integer gstarBoardId){
		repository.delete(gstarBoardId);
	}
	
	@Async
	public void sendMessageToAllUser(String title, String message) {
		
		List<GstarUser> users = userService.getGstarUserAllList();
		
		for (GstarUser gstarUser : users) {
			
			try {
				sendMessage(title, message, gstarUser);
			} catch (Exception e) {
				LOGGER.error(e.toString(), e);
			}
		}
		
	}
	
	protected void sendMessage(String title, String message, GstarUser user) throws IOException {
		if (user.getFcmToken() != null && user.isMobileNoti()) {
			fcmSender.sendPushMessage(title, message, user.getFcmToken());
			
			LOGGER.info("fcm send ok. user.id={}", user.getId());
			
		} else if (user.getEmail() != null && user.isEmailNoti()) {
			mailSender.sendMail(title, message, user.getEmail());
			LOGGER.info("mail send ok. user.id={}", user.getId());
			
		} else {
			LOGGER.info("skip message. user.id={}", user.getId());
		}
	}

}
//end of GstarBoardService.java