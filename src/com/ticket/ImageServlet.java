package com.ticket;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class ImageServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Client user=new Client();
		user.getCookie();
		req.getSession().setAttribute("User", user);
		
		
		BufferedImage bi=new BufferedImage(300,150,BufferedImage.TYPE_INT_BGR);
		Graphics g=bi.getGraphics();
		Image image =ImageIO.read(user.imageDownload());
		g.fillRect(0, 0, 300, 150);
		g.drawImage(image, 0, 0, 300, 150, null);
		ImageIO.write(bi,"jpg",resp.getOutputStream());
	}
	
}
