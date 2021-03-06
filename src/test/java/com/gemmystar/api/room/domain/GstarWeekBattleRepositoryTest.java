/* 
 * Copyright (C) 2012-2015 Open Source Consulting, Inc. All rights reserved by Open Source Consulting, Inc.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * Revision History
 * Author			Date				Description
 * ---------------	----------------	------------
 * BongJin Kwon		2016. 8. 29.		First Draft.
 */
package com.gemmystar.api.room.domain;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gemmystar.api.GemmyApplication;
import com.gemmystar.api.contents.domain.GstarContents;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=GemmyApplication.class)
public class GstarWeekBattleRepositoryTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Autowired
	private GstarWeekBattleRepository weekBattleRepo;

	@Test
	public void testGetCurrentWeekBattle() {
		try {
			GstarWeekBattle weekBattle = weekBattleRepo.getCurrentWeekBattle();
			
			assertNotNull(weekBattle);
		} catch(Exception e) {
			fail(e.toString());
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetWeekBattleWinners() {
		try {
			List<GstarContents> list = weekBattleRepo.getWeekBattleWinners(1, 1);
		} catch(Exception e) {
			fail(e.toString());
			e.printStackTrace();
		}
	}

}
//end of GstarWeekBattleRepositoryTest.java