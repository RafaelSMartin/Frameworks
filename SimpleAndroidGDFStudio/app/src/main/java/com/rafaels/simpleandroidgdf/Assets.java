//
//Autor: Rafael S. Martin Gonzalez
//Fecha: 09/08/2016

package com.rafaels.simpleandroidgdf;

import java.io.IOException;
import java.io.InputStream;

import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

import com.rafaels.framework.animation.Animation;
import com.rafaels.framework.animation.Frame;

public class Assets {
	
	private static SoundPool soundPool;

	//Creacion de variables Assets
	private static MediaPlayer mediaPlayer;
	public static Bitmap welcome, fondo;
	public static Bitmap run1, run2, run3, run4, run5;
	public static Animation runAnimation;

	//Creacion de variables ID
	public static int gameOverID;

	//Carga de Assets
	public static void load(){
		welcome = loadBitmap("welcome.png", false);
		fondo = loadBitmap("fondo.png", true);



		//Contruir la carga de una animacion
		run1 = loadBitmap("run_anim1.png", true);
		run2 = loadBitmap("run_anim2.png", true);
		run3 = loadBitmap("run_anim3.png", true);
		run4 = loadBitmap("run_anim4.png", true);
		run5 = loadBitmap("run_anim5.png", true);

		Frame f1 = new Frame(run1, .1f);
		Frame f2 = new Frame(run2, .1f);
		Frame f3 = new Frame(run3, .1f);
		Frame f4 = new Frame(run4, .1f);
		Frame f5 = new Frame(run5, .1f);
		runAnimation = new Animation(f1, f2, f3, f4, f5, f3, f2);
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

	private static int loadSound(String filename) {
		int soundID = 0;
		if (soundPool == null) {
			soundPool = buildSoundPool();
		}
		try {
			soundID = soundPool.load(GameMainActivity.assets.openFd(filename), 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return soundID;
	}
	
	//Se creara un solo objeto SoundPool como gestor de cada unos de los sonidos
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private static SoundPool buildSoundPool() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			AudioAttributes audioAttributes = new AudioAttributes.Builder()
					.setUsage(AudioAttributes.USAGE_GAME)
					.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
					.build();

			soundPool = new SoundPool.Builder()
					.setMaxStreams(25)
					.setAudioAttributes(audioAttributes)
					.build();
		} else {
			soundPool = new SoundPool(25, AudioManager.STREAM_MUSIC, 0);
		}
		return soundPool;
	}

	public static void playSound(int soundID){
		//Antes de poner el sonido, compruema si el juego esta en mute y devuelve lo necesario.
//		if(GameMainActivity.isMuted()) {
//			return;
//		}

		if(soundPool != null){
			soundPool.play(soundID, 1, 1, 1, 0, 1);
		}
	}

	public static void playMusic(String filename, boolean looping){
		//Antes de poner el sonido, compruema si el juego esta en mute y devuelve lo necesario.
//		if(GameMainActivity.isMuted()){
//			return;
//		}

		if(mediaPlayer == null){
			mediaPlayer = new MediaPlayer();
		}
		try{
			AssetFileDescriptor afd = GameMainActivity.assets.openFd(filename);
			mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.prepare();
			mediaPlayer.setLooping(looping);
			mediaPlayer.start();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//Llama cuando se muta y pausa el juego	.
	public static void onMute() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
		}
	}

	//Llama cuando se vuelve la musica al juego.
	public static void onUnmute() {
		if (mediaPlayer != null) {
			mediaPlayer.start();
		} else {
			playMusic("bgmusic.mp3", true);
		}
	}

	//Sera llamado por GameMainActivity.onResume() invocado por el sistema.
	public static void onResume(){
		gameOverID = loadSound("gameOver.wav");
		playMusic("bgmusic.mp3", true);
	}

	//Sera llamado por GameMainActivity.onPause() y eliminamos soundPool para liberar recursos.
	public static void onPause(){
		if(soundPool != null){
			soundPool.release();
			soundPool = null;
		}

		if(mediaPlayer != null){
			//mediaPlayer.stop();
			//mediaPlayer.release();
			//mediaPlayer = null;
			onStop();
		}
	}

	public static void onStop(){
		if(mediaPlayer != null){
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}

	}


}
