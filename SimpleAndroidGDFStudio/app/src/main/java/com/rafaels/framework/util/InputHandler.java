//Esta clase interactua con el usuario cuando presiona la pantalla.
//Los metodos de interaccion de currentState seran invocados cuando el jugador interactue.
//Se recibiran notificaciones siempre que el jugador interactue.
//InputHandler es un despachador/Listener q despacha a currentState las notificaciones del usuario.
//Autor: Rafael S. Martin Gonzalez
//Fecha: 09/08/2016

package com.rafaels.framework.util;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.rafaels.game.state.State;
import com.rafaels.simpleandroidgdf.GameMainActivity;

public class InputHandler implements OnTouchListener{

	private State currentState;
	
	public void setCurrentState(State currentState){
		this.currentState = currentState;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int scaledX = (int) ((event.getX() / v.getWidth()) * GameMainActivity.GAME_WIDTH);
		int scaledY = (int) ((event.getY() / v.getHeight()) * GameMainActivity.GAME_HEIGHT);
		return currentState.onTouch(event, scaledX, scaledY);
	}

}
