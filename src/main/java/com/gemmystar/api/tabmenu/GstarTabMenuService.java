package com.gemmystar.api.tabmenu;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.gemmystar.api.tabmenu.domain.GstarTabMenu;
import com.gemmystar.api.tabmenu.domain.GstarTabMenuRepository;


/**
 * <pre>
 * 
 * </pre>
 * @author Bong-Jin Kwon
 * @version 1.0
 */
@Service
public class GstarTabMenuService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GstarTabMenuService.class);

	@Autowired
	private GstarTabMenuRepository repository;
	
	public GstarTabMenuService() {
		// TODO Auto-generated constructor stub
	}
	
	public void save(GstarTabMenu gstarTabMenu){
		repository.save(gstarTabMenu);
	}
	
	public List<GstarTabMenu> getGstarTabMenuAllList(){
		return repository.findAll();
	}
	
	public Page<GstarTabMenu> getGstarTabMenuList(Pageable pageable, String search){
	
		/*
		Specifications<GstarTabMenu> spec = Specifications.where(GstarTabMenuSpecs.notBattle()).and(GstarTabMenuSpecs.notDeteled());
		
		if (search != null) {
			spec = spec.and(GstarTabMenuSpecs.search(search));
		}
		
		return repository.findAll(spec, pageable);
		*/
		
		return repository.findAll(pageable);
	}
	
	public List<GstarTabMenu> getShownGstarTabMenuList(){
		
		
		return repository.findByHiddenOrderByOrderSeqAsc(false);
	}
	
	/*
	public int getGstarTabMenuListTotalCount(GridParam gridParam){
		
		return repository.getGstarTabMenuListTotalCount(gridParam);
	}
	*/
	
	public GstarTabMenu getGstarTabMenu(Integer gstarTabMenuId){
		return repository.findOne(gstarTabMenuId);
	}
	
	/*
	public void updateGstarTabMenu(GstarTabMenu gstarTabMenu){
		repository.updateGstarTabMenu(gstarTabMenu);
	}
	*/
	
	public void deleteGstarTabMenu(Integer gstarTabMenuId){
		repository.delete(gstarTabMenuId);
	}

}
//end of GstarTabMenuService.java