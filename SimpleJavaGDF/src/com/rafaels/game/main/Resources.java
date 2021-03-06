package com.rafaels.game.main;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;



public class Resources {

	public static BufferedImage welcome, iconimage;
	
	public static void load(){
		welcome = loadImage("welcome.png");
		iconimage = loadImage("iconimage.png");
	}
	
	private static AudioClip loadSound(String filename){
		URL fileURL = Resources.class.getResource("/resources/" + filename);
		return Applet.newAudioClip(fileURL);		
	}
	
	private static BufferedImage loadImage(String filename){
		BufferedImage img = null;
		try {
			img = ImageIO.read(Resources.class.getResourceAsStream("/resources/" + filename));
		} catch (Exception e) {
			System.out.println("Error de lectura: " + filename);
			e.printStackTrace();
		}
		return img;
	}
}
