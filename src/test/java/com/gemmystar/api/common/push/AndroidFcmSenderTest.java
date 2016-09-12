package com.gemmystar.api.common.push;

import static org.junit.Assert.*;

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
public class AndroidFcmSenderTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}
	
	@Autowired
	private AndroidFcmSender sender;

	@Test
	public void testSendPushMessage() {
		
		try {
			sender.sendPushMessage("test message", "cQV38jVuaFs:APA91bE1vNswl7qqZqeEYjCaYuIRzbcoWG-JMQCijfrIrhslGAFFpUJiW7lwjjNIIopyCkYZEJZO6V1gqzKcSJRm27TtCmGM7HojQOfu2PLnVbRn0W8MbeidOvicFlA-uLtBiKIeTIdH");
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
		
	}

}
