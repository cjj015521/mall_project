package com.taotao.controller;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.taotao.common.utils.FtpUtil;

public class FTPTest {
	
	@Test
	public void testFtpClient() throws Exception {
		
		// 创建一个FtpClient对象
		FTPClient ftpClien = new FTPClient();
		
		// 创建ftp连接。默认端口是21端口
		ftpClien.connect("192.168.64.131", 21);
		
		// 登录ftp服务器，使用用户名和密码
		ftpClien.login("ftpuser", "123");
		
		// 上传文件
		// 读取本地文件
		FileInputStream inputStream = new FileInputStream(
				new File("D:\\1.jpg"));
		
		// 设置上传路径
		ftpClien.changeWorkingDirectory("/home/ftpuser/images");
		
		// 第一个参数：服务器端文档名
		// 第二个参数：上传文档的inputStream
		ftpClien.storeFile("hello1.jpg", inputStream);
		
		// 关闭连接
		ftpClien.logout();
	}
	
	@Test
	public void testFtpUtil() throws Exception {
		
		// 上传文件,读取本地文件
		FileInputStream inputStream = new FileInputStream(
														new File("E:\\wff.jpg"));
		
		FtpUtil.uploadFile("192.168.64.131", 21, "ftpuser", 
				"123", "/home/ftpuser/images", "/2020/06/30", "wff2.jpg", inputStream);
	}

}
