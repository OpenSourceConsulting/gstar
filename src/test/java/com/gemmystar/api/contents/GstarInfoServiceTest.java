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
public class GstarInfoServiceTest {

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
	private GstarInfoService infoService;

	/**
	 * thread 10 각 thread당 100번의 loop로 'gi.viewCnt = gi.viewCnt + 1' update 구분 테스트.
	 */
	@Test
	public void testIncreaseViewCnt() {
		
		final Long start = System.currentTimeMillis();
		
		final Long gstarContentsId = 24L;
		final int loop = 100;
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				System.out.println("start. " + Thread.currentThread().getId());
				
				for (int i = 0; i < loop; i++) {
					infoService.increaseViewCnt(gstarContentsId);
				}
				System.out.println("finished. " + loop + ", elp:" + (System.currentTimeMillis() - start));
			}
			
		}).start();
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				System.out.println("start. " + Thread.currentThread().getId());
				
				for (int i = 0; i < loop; i++) {
					infoService.increaseViewCnt(gstarContentsId);
				}
				System.out.println("finished. " + loop + ", elp:" + (System.currentTimeMillis() - start));
			}
			
		}).start();
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				System.out.println("start. " + Thread.currentThread().getId());
				
				for (int i = 0; i < loop; i++) {
					infoService.increaseViewCnt(gstarContentsId);
				}
				System.out.println("finished. " + loop+ ", elp:" + (System.currentTimeMillis() - start));
			}
			
		}).start();
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				System.out.println("start. " + Thread.currentThread().getId());
				
				for (int i = 0; i < loop; i++) {
					infoService.increaseViewCnt(gstarContentsId);
				}
				System.out.println("finished. " + loop+ ", elp:" + (System.currentTimeMillis() - start));
			}
			
		}).start();
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				System.out.println("start. " + Thread.currentThread().getId());
				
				for (int i = 0; i < loop; i++) {
					infoService.increaseViewCnt(gstarContentsId);
				}
				System.out.println("finished. " + loop+ ", elp:" + (System.currentTimeMillis() - start));
			}
			
		}).start();
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				System.out.println("start. " + Thread.currentThread().getId());
				
				for (int i = 0; i < loop; i++) {
					infoService.increaseViewCnt(gstarContentsId);
				}
				System.out.println("finished. " + loop+ ", elp:" + (System.currentTimeMillis() - start));
			}
			
		}).start();
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				System.out.println("start. " + Thread.currentThread().getId());
				
				for (int i = 0; i < loop; i++) {
					infoService.increaseViewCnt(gstarContentsId);
				}
				System.out.println("finished. " + loop+ ", elp:" + (System.currentTimeMillis() - start));
			}
			
		}).start();
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				System.out.println("start. " + Thread.currentThread().getId());
				
				for (int i = 0; i < loop; i++) {
					infoService.increaseViewCnt(gstarContentsId);
				}
				System.out.println("finished. " + loop+ ", elp:" + (System.currentTimeMillis() - start));
			}
			
		}).start();
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				System.out.println("start. " + Thread.currentThread().getId());
				
				for (int i = 0; i < loop; i++) {
					infoService.increaseViewCnt(gstarContentsId);
				}
				System.out.println("finished. " + loop+ ", elp:" + (System.currentTimeMillis() - start));
			}
			
		}).start();
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				System.out.println("start. " + Thread.currentThread().getId());
				
				for (int i = 0; i < loop; i++) {
					infoService.increaseViewCnt(gstarContentsId);
				}
				System.out.println("finished. " + loop+ ", elp:" + (System.currentTimeMillis() - start));
			}
			
		}).start();
		/*
		*/
		
		
		try {
			Thread.sleep(11000);
		} catch (Exception e) {
			//ignore.
		}
	}

}
