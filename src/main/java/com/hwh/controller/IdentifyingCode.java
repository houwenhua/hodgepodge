package com.hwh.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;


/**
 * 生成登录时的验证码
 */
@Controller
public class IdentifyingCode {
    private static Log logger = LogFactory.getLog(IdentifyingCode.class);

    @RequestMapping("/code")
    public void code(HttpServletResponse response) throws IOException {
        logger.info("进入验证码生成方法");

        //创建一张图片
        int width = 60;
        int height = 30;
        BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();

        //设置背景
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0,0,width-1,height-1);

        //画一个边框
        g.setColor(Color.black);
        g.drawRect(0,0,width,height);

        //生成随机值
        Random random = new Random();
        String hashvalue =  Integer.toHexString(random.nextInt());//转换成16进制的字符串
        logger.info("生成的验证码值："+hashvalue);

        //画椭圆，也就是生成麻子点点
        for(int i = 0; i < 50; i++){
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            g.drawOval(x,y,0,0);
        }

        //获取4个字符
        String code = hashvalue.substring(0,4);
        g.setColor(new Color(0,100,0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        //画入字符串
        g.drawString(code, 7, 22);
        g.dispose();//垃圾回收

        response.setContentType("image/jpeg");
        OutputStream strm = response.getOutputStream();
        ImageIO.write(img, "jpeg", strm);
        strm.close();
    }
}
