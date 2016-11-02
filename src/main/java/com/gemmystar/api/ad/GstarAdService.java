package com.gemmystar.api.ad;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.gemmystar.api.ad.domain.GstarAd;
import com.gemmystar.api.ad.domain.GstarAdRepository;


/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class GstarAdService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GstarAdService.class);

	@Autowired
	private GstarAdRepository repository;
	
	public GstarAdService() {
		// TODO Auto-generated constructor stub
	}
	
	public void save(GstarAd gstarAd){
		repository.save(gstarAd);
	}
	
	public List<GstarAd> getGstarAdAllList(){
		return repository.findAll();
	}
	
	public Page<GstarAd> getGstarAdList(Pageable pageable, String search){
	
		/*
		Specifications<GstarAd> spec = Specifications.where(GstarAdSpecs.notBattle()).and(GstarAdSpecs.notDeteled());
		
		if (search != null) {
			spec = spec.and(GstarAdSpecs.search(search));
		}
		
		return repository.findAll(spec, pageable);
		*/
		
		return repository.findAll(pageable);
	}
	
	public Page<GstarAd> getCurrentAdList(Pageable pageable){
		
		return repository.findCurrentAds(pageable);
	}
	
	
	public GstarAd getGstarAd(Integer gstarAdId){
		return repository.findOne(gstarAdId);
	}
	
	
	public void deleteGstarAd(Integer gstarAdId){
		repository.delete(gstarAdId);
	}
	
	public void deleteGstarAd(GstarAd gstarAd){
		repository.delete(gstarAd);
	}

}
//end of GstarAdService.java