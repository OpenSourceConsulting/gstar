package com.gemmystar.api.room.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gemmystar.api.room.domain.GstarRoomContentsWeek;

/**
 * GstarRoomContentsWeekRepository
 *
 * @author Bongjin Kwon
 * @version 1.0
 */
@Repository
public interface GstarRoomContentsWeekRepository extends JpaRepository<GstarRoomContentsWeek, Long> {

	
}