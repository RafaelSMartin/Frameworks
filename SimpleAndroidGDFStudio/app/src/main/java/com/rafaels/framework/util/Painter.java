//Clase intermedia entre nuestro estado y el objeto GameImage de tipo Canvas
//Crea y actualiza los objetos rectangle por cuenta de los estados del juego y sus objetos
//El objeto canvas dentro de la clase pertenecera a GameImage, para renderizar solo pedimos a painter que dibuje
//Se usa para estabecer la fuente y tamano de TypeFace, el color de poligonos dibujados,...
//Autor: Rafael S. Martin Gonzalez
//Fecha: 09/08/2016

package com.rafaels.framework.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;

public class Painter {
	
	private Canvas canvas;
	private Paint paint;
	private Rect srcRect;
	private Rect dstRect;
	//Para valores float
	private RectF dstRectF;
	
	//Constructor
	public Painter(Canvas canvas){
		this.canvas = canvas;
		paint = new Paint();
		srcRect = new Rect();
		dstRect = new Rect();
		dstRectF = new RectF();
	}
	
	public void setColor(int color){
		paint.setColor(color);
	}
	
	public void setFont(Typeface typeface, float textSize){
		paint.setTypeface(typeface);
		paint.setTextSize(textSize);
	}
	
	public void drawString(String str, int x, int y){
		canvas.drawText(str, x, y, paint);
	}
	
	public void fillRect(int x, int y, int width, int height){
		dstRect.set(x, y, x + width, y + height);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawRect(dstRect, paint);
	}
	
	public void drawImage(Bitmap bitmap, int x, int y){
		canvas.drawBitmap(bitmap, x, y, paint);
	}

	public void drawImage(Bitmap bitmap, int x, int y, int width, int height){
		srcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
		dstRect.set(x, y, x + width, y + height);
		canvas.drawBitmap(bitmap, srcRect, dstRect, paint);
	}
	
	public void fillOval(int x, int y, int width, int height){
		paint.setStyle(Paint.Style.FILL);
		dstRectF.set(x, y, x + width, y + height);
		canvas.drawOval(dstRectF, paint);		
	}
}
