package com.gemmystar.api.user;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.gemmystar.api.GemmyConstant;
import com.gemmystar.api.user.domain.GstarAccount;
import com.gemmystar.api.user.domain.GstarAccountAuth;
import com.gemmystar.api.user.domain.GstarAccountAuthRepository;
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private GstarAccountAuthRepository authRepo;
	
	public GstarAccountService() {
		
	}
	
	public void save(GstarAccount gstarAccount){
		repository.save(gstarAccount);
	}
	
	public void saveRole(Long gstarAccountId, String[] roles) {
		
		for (String role : roles) {
			GstarAccountAuth auth = new GstarAccountAuth(gstarAccountId, role);
			authRepo.save(auth);
		}
	}
	
	public void deleteRole(Long gstarAccountId) {
		authRepo.deleteByGstarAccountId(gstarAccountId);
	}
	
	public List<GstarAccount> getGstarAccountAllList(){
		return repository.findAll();
	}
	
	public List<GstarAccount> getGstarAccountList(Long gstarUserId){
		return repository.findByGstarUserId(gstarUserId);
	}
	
	public GstarAccount getGstarAccount(Long accountId){
		return repository.findOne(accountId);
	}
	
	public GstarAccount getGstarAccountByLoginId(String loginId) {
		return repository.findByLoginId(loginId);
	}

	/**
	 * admin 계정만 명시적으로 삭제한다.
	 * @param gstarUserId
	 */
	@Transactional
	public void deleteAdminAccountByUserId(Long gstarUserId, GstarAccount account) {
		
		Assert.isTrue(GemmyConstant.CODE_ACCOUNT_TYPE_ADMIN.equals(account.getAccountTypeCd()), "삭제하려는 계정이 관리자 계정이 아닙니다.");
		
		if(tokenRepo.findOne(account.getId()) != null) {
			
			tokenRepo.delete(account.getId());//위 if문 없으면 에러남.
		}
		
		repository.delete(account);
	}
	
	@Transactional
	public void deleteAccountByUserId(Long gstarUserId, List<GstarAccount> accounts) {
		
		
		for (GstarAccount gstarAccount : accounts) {
			
			if(tokenRepo.findOne(gstarAccount.getId()) != null) {
				
				tokenRepo.delete(gstarAccount.getId());//위 if문 없으면 에러남.
			}
			
			repository.delete(gstarAccount);
		}
		
	}
	
	public void deleteGstarAccount(Long accountId){
		repository.delete(accountId);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByLoginId(username);
	}
	
	@Transactional
	public String createPasswordResetToken(GstarAccount account) { 
		
		account.setLocked(true);
		repository.save(account);
		
		String token = UUID.randomUUID().toString();
		
		GstarPassresetToken tokenEntity = new GstarPassresetToken(account.getId(), token, calculateExpiryDate());
		
		tokenRepo.save(tokenEntity);
		
		return token;
	}
	
	private Date calculateExpiryDate() {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, EXPIRATION);
        return new Date(cal.getTime().getTime());
    }
	
	public GstarPassresetToken getPasswordResetToken(String token) {
		return tokenRepo.findByToken(token);
	}
	
	@Transactional
	public void changeUserPassword(GstarAccount gstarAccount, String password) {
		
		gstarAccount.setLocked(false);
		gstarAccount.setPasswd(passwordEncoder.encode(password));
		
		repository.save(gstarAccount);
		
		tokenRepo.updateExpireDt(gstarAccount.getId());
		
	}

}
//end of GstarAccountService.java