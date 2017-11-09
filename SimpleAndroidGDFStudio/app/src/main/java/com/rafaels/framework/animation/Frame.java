
//Clase que almacena un objeto Bitmap especifico de android
//Autor: Rafael S. Martin Gonzalez
//Fecha: 13/08/2016

package com.rafaels.framework.animation;

import android.graphics.Bitmap;

public class Frame {
	private Bitmap image;
	private double duration;
	
	public Frame(Bitmap image, double duration){
		this.image = image;
		this.duration = duration;
	}
	
	public Bitmap getImage(){
		return image;
	}

	public double getDuration(){
		return duration;
	}
}
