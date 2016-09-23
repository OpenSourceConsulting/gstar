package com.gemmystar.api.board;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gemmystar.api.board.domain.GstarBoard;
import com.gemmystar.api.board.domain.GstarBoardRepository;


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
	
	/*
	public int getGstarBoardListTotalCount(GridParam gridParam){
		
		return repository.getGstarBoardListTotalCount(gridParam);
	}
	*/
	
	public GstarBoard getGstarBoard(Integer gstarBoardId){
		return repository.findOne(gstarBoardId);
	}
	
	/*
	public void updateGstarBoard(GstarBoard gstarBoard){
		repository.updateGstarBoard(gstarBoard);
	}
	*/
	
	public void deleteGstarBoard(Integer gstarBoardId){
		repository.delete(gstarBoardId);
	}

}
//end of GstarBoardService.java