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
 * BongJin Kwon		2016. 8. 3.		First Draft.
 */
package com.gemmystar.api.youtube;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;

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
public class YoutubeServiceTest {

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
	private YoutubeService youtubeService;

	@Test
	public void testUploadVideo() {
		
		File videoFile = new File("G:\\down\\20160803_131911.mp4");
		
		try {
			if (videoFile.exists()) {
				
				GstarContents contents = new GstarContents();
				Calendar cal = Calendar.getInstance();
				contents.setSubject("Test Upload via Java on " + cal.getTime());
				contents.setMemo("Video uploaded via YouTube Data API V3 using the Java library " + "on " + cal.getTime());
				
				youtubeService.uploadVideo(new FileInputStream(videoFile), videoFile.length(), contents);
			}
		} catch (Exception e) {
			fail(e.toString());
		}
		
	}
	
	@Test
	public void testDeleteVideo() {
		
		try {
			youtubeService.deleteVideo("1owGvgt8TRM");
		} catch (Exception e) {
			fail(e.toString());
			e.printStackTrace();
		}
	}

}
//end of YoutubeServiceTest.java