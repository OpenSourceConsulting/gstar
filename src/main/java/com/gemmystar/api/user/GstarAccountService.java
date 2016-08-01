package com.gemmystar.api.user;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gemmystar.api.user.domain.GstarAccount;
import com.gemmystar.api.user.domain.GstarAccountRepository;
import com.gemmystar.api.user.domain.GstarPassresetToken;
import com.gemmystar.api.user.domain.GstarPassresetTokenRepository;

/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class GstarAccountService implements UserDetailsService {
	
	private static final int EXPIRATION = 60 * 24;

	@Autowired
	private GstarAccountRepository repository;
	
	@Autowired
	private GstarPassresetTokenRepository tokenRepo;
	
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
	
	public String createPasswordResetToken(Long accountId) { 
		String token = UUID.randomUUID().toString();
		
		GstarPassresetToken tokenEntity = new GstarPassresetToken(accountId, token, calculateExpiryDate());
		
		tokenRepo.save(tokenEntity);
		
		return token;
	}
	
	private Date calculateExpiryDate() {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, EXPIRATION);
        return new Date(cal.getTime().getTime());
    }

}
//end of GstarAccountService.java