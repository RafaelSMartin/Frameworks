//Autor: Rafael S. Martin Gonzalez
//Fecha: 09/08/2016

package com.rafaels.game.state;

import com.rafaels.framework.util.Painter;
import com.rafaels.simpleandroidgdf.Assets;

import android.view.MotionEvent;

public class MenuState extends State{

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}
	//Dibuja en pantalla la imagen de bienvenida
	@Override
	public void render(Painter g) {
		g.drawImage(Assets.welcome, 0, 0);
		
	}

	@Override
	public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
		// TODO Auto-generated method stub
		return false;
	}

}
