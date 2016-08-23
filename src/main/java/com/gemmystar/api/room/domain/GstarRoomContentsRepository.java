package com.gemmystar.api.room.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gemmystar.api.room.domain.GstarRoomContents;

/**
 * GstarRoomContentsRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarRoomContentsRepository extends JpaRepository<GstarRoomContents, Long> {

	
}