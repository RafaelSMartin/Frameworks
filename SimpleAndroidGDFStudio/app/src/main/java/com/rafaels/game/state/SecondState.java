package com.rafaels.game.state;

import android.view.MotionEvent;

import com.rafaels.framework.util.Painter;
import com.rafaels.simpleandroidgdf.Assets;

/**
 * Created by Indogroup02 on 09/11/2017.
 */



public class SecondState extends State {

    //Guarda la coordenada X e Y del toque mas reciente
    private float recentTouchY, recentTouchX;


    @Override
    public void init() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.fondo, 0, 0);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if(e.getAction() == MotionEvent.ACTION_DOWN){
            recentTouchY = scaledY;
            recentTouchX = scaledX;

        }else if (e.getAction() == MotionEvent.ACTION_UP){
            setCurrentSate(new MenuState());
        }
        return true;
    }
}
