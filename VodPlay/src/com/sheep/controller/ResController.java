package com.sheep.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ResController {

	@RequestMapping("/pictureUrl")
	public void getUrl(HttpServletResponse response, HttpServletRequest request) {
		String filePath = request.getParameter("url");
		FileInputStream fis = null;
		File file = new File(filePath);
		try {
			fis = new FileInputStream(file);
			response.setContentType("image/jpg"); // ���÷��ص��ļ�����
			response.setHeader("Access-Control-Allow-Origin", "*");// ���ø��ļ�����������
			IOUtils.copy(fis, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@RequestMapping("/getMovie")
	public void getMovie(HttpServletResponse response, HttpServletRequest request) {
		String filePath = request.getParameter("url");
		FileInputStream fis = null;
		File file = new File(filePath);
		try {
			fis = new FileInputStream(file);
			response.setContentType("video/mp4"); // ���÷��ص��ļ�����
			response.setHeader("Access-Control-Allow-Origin", "*");// ���ø�ͼƬ����������
			IOUtils.copy(fis, response.getOutputStream());
		} catch (IOException e) {
//			e.printStackTrace();
		}finally {
			if(fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
