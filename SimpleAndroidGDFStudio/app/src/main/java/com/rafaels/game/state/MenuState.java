//Autor: Rafael S. Martin Gonzalez
//Fecha: 09/08/2016

package com.rafaels.game.state;

import com.rafaels.framework.util.Painter;
import com.rafaels.framework.util.UIButton;
import com.rafaels.simpleandroidgdf.Assets;

import android.view.MotionEvent;

public class MenuState extends State{

	private UIButton button;

	//Inicializamos los botones Rect en las coordenadas precisas
	@Override
	public void init() {
		button = new UIButton(0,0,800,450, Assets.run3, Assets.run5);

		Assets.onStop();
//		Assets.playMusic("bgmusic.mp3", true);
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}
	//Dibuja en pantalla la imagen de bienvenida
	@Override
	public void render(Painter g) {
		g.drawImage(Assets.welcome, 0, 0);
		button.render(g);
		
	}

	//Solo un boton debera estar pulsado a la vez
	@Override
	public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
		if (e.getAction() == MotionEvent.ACTION_DOWN) {
			button.onTouchDown(scaledX,scaledY);
		}
		if(e.getAction() == MotionEvent.ACTION_UP){
			if(button.isPressed(scaledX,scaledY)){
				button.cancel();
				setCurrentSate(new SecondState());
			}
		}
		return true;
	}

}
