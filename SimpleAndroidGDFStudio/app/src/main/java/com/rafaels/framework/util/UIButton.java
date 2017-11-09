//Clase que encapsula toda la logica necesaria para crear un boton y manejar las pulsaciones
//y renderizado de los estados del boton.
//Autor: Rafael S. Martin Gonzalez
//Fecha: 16/08/2016

package com.rafaels.framework.util;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class UIButton {
	
	private Rect buttonRect;
	private boolean buttonDown;
	private Bitmap buttonImage, buttonDownImage;
	
	//Contructor
	// izquierda, arriba, derecha, abajo
	public UIButton(int left, int top, int right, int botton, Bitmap buttonImage, Bitmap buttonPressedImage){
		buttonRect = new Rect(left, top, right, botton);
		this.buttonImage = buttonImage;
		this.buttonDownImage = buttonPressedImage;
	}
	
	public void render(Painter g){
		// if (buttonDown){currentButtonImage = buttonDownImage;} else {curentButtonImage = buttonImage;}
		Bitmap currentButtonImage = buttonDown ? buttonDownImage : buttonImage;
		g.drawImage(currentButtonImage, buttonRect.left, buttonRect.top, buttonRect.width(), buttonRect.height());
	}
	
	public void onTouchDown(int touchX, int touchY){
		if (buttonRect.contains(touchX, touchY)){
			buttonDown = true;
		} else {
			buttonDown = false;
		}
	}
	
	public void cancel(){
		buttonDown = false;
	}
	
	public boolean isPressed(int touchX, int touchY){
		return buttonDown && buttonRect.contains(touchX, touchY);
	}

}
