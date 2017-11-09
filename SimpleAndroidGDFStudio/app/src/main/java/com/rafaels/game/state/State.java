//Clase abstacta que da una estructura comun a los otras clases de estados
//Autor: Rafael S. Martin Gonzalez
//Fecha: 09/08/2016

package com.rafaels.game.state;

import com.rafaels.framework.util.Painter;
import com.rafaels.simpleandroidgdf.GameMainActivity;

import android.view.MotionEvent;

public abstract class State {
	
	public void setCurrentSate(State newState){
		GameMainActivity.sGame.setCurrentState(newState);
	}
	
	//Se invocara cuando el juego cambie de estado, se pueden inicializar objetos.
	public abstract void init();
	
	//Acualizaremos todos los objetos del juego en cada frame.
	//El update del estado actual lo invocara en el bucle del juego en cada frame.
	public abstract void update(float delta);
	
	//Mostraremos imagenes del juego en cada frame.
	//El render del estado actual lo invocara en el bucle del juego en cada frame.
	public abstract void render(Painter g);
	
	//Se invocara cuando por InputHandler cuando se toque la pantalla tactil
	//MotionEvent ofrece info sobre el evento tactil
	public abstract boolean onTouch(MotionEvent e, int scaledX, int scaledY);

	public void onResume() {}

	public void onPause() {}

}
