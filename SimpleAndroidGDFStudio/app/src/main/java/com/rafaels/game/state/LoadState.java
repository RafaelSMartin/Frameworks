//Autor: Rafael S. Martin Gonzalez
//Fecha: 09/08/2016

package com.rafaels.game.state;

import com.rafaels.framework.util.Painter;
import com.rafaels.simpleandroidgdf.Assets;

import android.view.MotionEvent;

public class LoadState extends State{

	@Override
	public void init() {
		Assets.load();		
	}
	//Al invocar LoadState.update() pasamos de LoadState a MenuState
	@Override
	public void update(float delta) {
		setCurrentSate(new MenuState());
		
	}

	@Override
	public void render(Painter g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
		// TODO Auto-generated method stub
		return false;
	}

}
