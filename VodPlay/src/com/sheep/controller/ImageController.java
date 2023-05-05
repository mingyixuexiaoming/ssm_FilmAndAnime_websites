package com.sheep.controller;

import java.awt.Color;  
import java.awt.Font;  
import java.awt.Graphics;  
import java.awt.image.BufferedImage;  
import java.io.IOException;  
import java.io.OutputStream;  
import java.util.Random;  
  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;  
  
@Controller  
public class ImageController {  
      
    String[] random = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",  
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",  
            "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",  
            "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",  
            "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };  
  
	@RequestMapping("/chkcode")
    public void chkcodeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {  
        int width = 130;  
        int height = 50;  
        // ������Ӧ��ʽ  
        response.setContentType("image/jpeg");  
        // ��������  
        OutputStream os = response.getOutputStream();  
        // ����ͼƬ�ڴ���� ���������� �͸�ʽ  
        BufferedImage image = new BufferedImage(width, height,  
                BufferedImage.TYPE_INT_RGB);  
        // ��������  
        Graphics g = image.createGraphics();  
        Random ran = new Random();  
        // �ȸ�����������ɫ  
        g.setColor(new Color(ran.nextInt(256), ran.nextInt(256), ran  
                .nextInt(256)));  
        // ������  
        g.fillRect(0, 0, width, height);  
        // �������� Font(String name,int style,int size)ָ�����ơ���ʽ�Ͱ�ֵ��С  
        Font font = new Font("����", Font.BOLD, 36);  
        g.setFont(font);  
        //����������֤����Ϣ  
        StringBuffer sb = new StringBuffer();  
        //������֤��λ��  
        int[] wid = {15,45,75,105};  
        int[] hei = {35,40,30,28};  
        // ������λ��֤��  
        for(int i=0;i<wid.length;i++){  
            String str = random[ran.nextInt(62)];  
            sb.append(str);  
            g.setColor(new Color(ran.nextInt(256), ran.nextInt(256), ran  
                    .nextInt(256)));  
            g.drawString(str, wid[i], hei[i]);  
        }  
  
        //����֤�뷢��request��  
        request.setAttribute("chkCode", sb.toString());
        // ���Ƹ�����  
        for (int i = 0; i < 6; i++) {  
            g.setColor(new Color(ran.nextInt(256), ran.nextInt(256), ran  
                    .nextInt(256)));  
            g.drawLine(ran.nextInt(width), ran.nextInt(height), ran  
                    .nextInt(width), ran.nextInt(height));  
        }  
  
    /*    // ��λͼתΪjpeg ��ʽ����  
        // ʹ��JPEGImageEncoder ����һ��ת��һ����� �����������  
        JPEGImageEncoder encode = JPEGCodec.createJPEGEncoder(os);  
  
        // ��BufferedImage�����е�ͼ����Ϣ�����  
        // �򴴽��ö���(encoder)ʱָ������������  
        encode.encode(image); */
    }  
    
}  
