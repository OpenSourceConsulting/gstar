package com.gemmystar.api.contents;

import static org.junit.Assert.*;

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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=GemmyApplication.class)
public class S3ServiceTest {

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
	private S3Service s3Service;

	@Test
	public void testDownload() {
		
		try {
			//s3Service.download("video/C17_zvieDaedgQQ.mp4");
			s3Service.download("video/C44_HBjwFTaZs8w.null");
			
		} catch (Exception e) {
			fail(e.toString());
			e.printStackTrace();
		}
	}

	@Test
	public void testRenameObject() {
		try {
			s3Service.renameObject("video/C44_HBjwFTaZs8w.null", "video/C44_HBjwFTaZs8w.mp4");
			
		} catch (Exception e) {
			fail(e.toString());
			e.printStackTrace();
		}
	}

}
