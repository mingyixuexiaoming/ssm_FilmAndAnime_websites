package com.sheep.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;


@Component
public class RandomValidateCode {

    /**
     * ���ɴ���
     * 
     * @return
     */
    public static String createValidateCode(int size) {
        String seed = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        int len = seed.length();
        char[] p = new char[size];
        for (int i = 0; i < size; i++) {
            p[i] = seed.charAt((int) Math.floor(Math.random() * len));
        }
        return new String(p);
    }

    private final Random random = new Random();

    private final String randString = "123456789ABCDEFGHIJKLMNPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";// ����������ַ���
    private final int width = 135;// ͼƬ��
    private final int height = 40;// ͼƬ��
    private final int lineSize = 50;// ����������
    private final int stringNum = 4;// ��������ַ�����

    private final int fontSize = 30;// ��������ַ�����

    /**
     * �������ͼƬ
     */
    public void getRandcode(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        // BufferedImage���Ǿ��л�������Image��,Image������������ͼ����Ϣ����
        BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();// ����Image�����Graphics����,�Ķ��������ͼ���Ͻ��и��ֻ��Ʋ���
        g.fillRect(0, 0, this.width, this.height);
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, this.fontSize));
        g.setColor(this.getRandColor(110, 133));
        // ���Ƹ�����
        for (int i = 0; i <= this.lineSize; i++) {
            this.drawLine(g);
        }
        // ��������ַ�
        String randomString = "";
        for (int i = 1; i <= this.stringNum; i++) {
            randomString = this.drawString(g, randomString, i);
        }
        session.removeAttribute(Constants.RANDOM_CODE_KEY);
        session.setAttribute(Constants.RANDOM_CODE_KEY, randomString);
        // System.out.println(randomString);
        g.dispose();
        try {
            // ��ֹͼ�񻺴档
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            // ���ڴ��е�ͼƬͨ��������ʽ������ͻ���
            ImageIO.write(image, "JPEG", response.getOutputStream());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ��ȡ������ַ�
     */
    public String getRandomString(int num) {
        return String.valueOf(this.randString.charAt(num));
    }

    /**
     * ���Ƹ�����
     */
    private void drawLine(Graphics g) {
        int x = this.random.nextInt(this.width);
        int y = this.random.nextInt(this.height);
        int xl = this.random.nextInt(13);
        int yl = this.random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }

    /**
     * �����ַ���
     */
    private String drawString(Graphics g, String randomString, int i) {
        g.setFont(this.getFont());
        g.setColor(new Color(this.random.nextInt(155), this.random.nextInt(123), this.random.nextInt(176)));
        String rand = String.valueOf(this.getRandomString(this.random.nextInt(this.randString.length())));
        randomString += rand;
        g.translate(this.random.nextInt(3), this.random.nextInt(3));
        g.drawString(rand, (this.width / this.stringNum - 14) * i, this.height - 7);
        return randomString;
    }

    /**
     * �������
     */
    private Font getFont() {
        return new Font("Times New Roman", Font.CENTER_BASELINE, this.fontSize);
    }

    /**
     * �����ɫ
     */
    private Color getRandColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + this.random.nextInt(bc - fc - 16);
        int g = fc + this.random.nextInt(bc - fc - 14);
        int b = fc + this.random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }
}