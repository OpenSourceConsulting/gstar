package com.gemmystar.api.contents;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.FileCopyUtils;

import com.gemmystar.api.GemmyApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=GemmyApplication.class)
public class GstarContentsServiceTest {

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
	private GstarContentsService service;

	@Test
	public void testUploadThumbnailFileToS3() {
		
		FileInputStream fis = null;
		try {
			
			File file = new File("G:\\project\\AthenaMeerkat\\capture\\visualVM_sample.PNG");
			fis = new FileInputStream(file);
			
			MockMultipartFile mFile = new MockMultipartFile(file.getName(), file.getName(), "image/png", fis);

			
			String imgUrl = service.uploadThumbnailFileToS3(mFile, 999L, "youtubeId");
			
			System.out.println("imgUrl: " + imgUrl);
			
		} catch (Exception e) {
			fail(e.toString());
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(fis);
		}
	}

}
