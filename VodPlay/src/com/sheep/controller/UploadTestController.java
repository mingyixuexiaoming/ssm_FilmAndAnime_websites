package com.sheep.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sheep.pojo.User;
import com.sheep.utils.Plupload;
import com.sheep.utils.PluploadService;

@Controller
public class UploadTestController {
	
	/**Plupload�ļ��ϴ�������*/  
    @RequestMapping(value="/toUpload/pluploadUpload")  
    public void upload(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,HttpSession session) {  
  
        plupload.setRequest(request);//�ֶ�����Plupload����HttpServletRequest����  
  
        User user = (User)request.getSession().getAttribute("user");  
        	
        //�ļ��洢����·��,����һ���ļ��У���Ŀ��ӦServlet�����µ�"pluploadDir"�ļ��У��������û�Ψһid������  
        File dir = new File("E:/myvod/video/"+user.getUserName());  
        if(!dir.exists()){  
            dir.mkdirs();//�ɴ����༶Ŀ¼����mkdir()ֻ�ܴ���һ��Ŀ¼  
        }  
        //��ʼ�ϴ��ļ�  
        PluploadService.upload(plupload, dir,session);  
    }  
	
}
