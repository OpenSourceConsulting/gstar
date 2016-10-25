package com.gemmystar.api.board;

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
public class GstarBoardServiceTest {

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
	private GstarBoardService service;

	@Test
	public void testSendMessageToAllUser() {
		
		try {
			service.sendMessageToAllUser("test title", "test message!!");// this is async.
			
			System.out.println("----------------------------------------------------------");
			Thread.sleep(8000);
			
		} catch (Exception e) {
			fail(e.toString());
			e.printStackTrace();
		}
	}

}
