package com.signalway;

import com.signalway.entity.fileentity.FileByteInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PolicemaintenanceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PolicemaintenanceApplicationTests {
	@LocalServerPort
	private int port;

	private URL base;

	@Autowired
	private TestRestTemplate restTemplate;
	@Test
	public void contextLoads() {
	}
	@Before
	public void setUp() throws Exception {
		String url = String.format("http://localhost:%d/", port);
		System.out.println(String.format("port is : [%d]", port));
		this.base = new URL(url);
	}

	/**
	 * 向"/test"地址发送请求，并打印返回结果
	 * @throws Exception
	 */
	@Test
	public void test1() {
		try{
			ResponseEntity<String> response = this.restTemplate.postForEntity(
					this.base.toString() + "/hello",
					HttpMethod.POST, String.class);
			System.out.println(String.format("测试结果为：%s", response.getBody()));
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void test2() {
		try{
			FileByteInfo fileByteInfo = new FileByteInfo();
			System.out.println(fileByteInfo.getAllFrameNum()+"");
			System.out.println(String.valueOf(fileByteInfo.getAllFrameNum()));
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
