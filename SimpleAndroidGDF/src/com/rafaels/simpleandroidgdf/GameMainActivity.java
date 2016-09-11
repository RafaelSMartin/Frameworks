//Clase Principal del framework, es el punto de entrada, la actividad que inicia la aplicaicion.
//Solo habra una actividad en el juego con un Surface dinamico que muestra el estado actual.
//Es donde se dibujara el juego y contendra una SurfaceView personalizada (GameView).
//Autor: Rafael S. Martin Gonzalez 
//Fecha: 13/08/2016

package com.rafaels.simpleandroidgdf;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.WindowManager;

public class GameMainActivity extends Activity{
	
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 450;
	public static GameView sGame;
	public static AssetManager assets;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//Carga archivos desde la carpeta assets
		assets = getAssets();
		//Crea una vista de tipo GameView y la guarda en sGame 
		sGame = new GameView(this, GAME_WIDTH, GAME_HEIGHT);
		//Añade contenido sGame a la Vista
		setContentView(sGame);
		//Hace que se mantenga la pantalla encendida en todo momento, para q no se apague mientras juegas.
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

}
