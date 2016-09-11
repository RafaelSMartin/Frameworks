//
//Autor: Rafael S. Martin Gonzalez
//Fecha: 09/08/2016

package com.rafaels.simpleandroidgdf;

import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.AudioManager;
import android.media.SoundPool;

public class Assets {
	
	private static SoundPool soundPool;
	public static Bitmap welcome;
	
	public static void load(){
		welcome = loadBitmap("welcome.png", false);
	}

	//Carga de la Imagen (filename) en tres etapas sabiendo la transparencia.
	//Primero se crea inputStream para leer datos desde el sistema de archivos que hay en assets
	//Segundo se crea option que dice la forma de guardado (si es transparente ocupara mas)
	//Tercero se crea bitmap usando la calse BitmapFactory pasando los anteriores como argumentos
	private static Bitmap loadBitmap(String filename, boolean transparency) {
		InputStream inputStream = null;
		try {
			inputStream = GameMainActivity.assets.open(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Options options = new Options();
		if (transparency) {
			options.inPreferredConfig = Config.ARGB_8888;
		} else {
			options.inPreferredConfig = Config.RGB_565;
		}
		Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null,
				options);
		return bitmap;
	}
	
	//Se creara un solo objeto SoundPool como gestor de cada unos de los sonidos
	@SuppressWarnings({ "deprecation", "unused" })
	private static int loadSound(String filename) {
		int soundID = 0;
		if (soundPool == null) {
			soundPool = new SoundPool(25, AudioManager.STREAM_MUSIC, 0);
		}
		try {
			soundID = soundPool.load(GameMainActivity.assets.openFd(filename), 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return soundID;
	}
	
	public static void playSound(int soundID){
		soundPool.play(soundID, 1, 1, 1, 0, 1);
	}

}
