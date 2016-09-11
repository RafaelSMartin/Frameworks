//GaneView es una clase personalizada de SurfaceView que fijaremos en GameMainActivity
//Aqui estara el bucle del juego. 
//Acepta las interacciones del usuario. 
//Actualiza el estado actual.
//Renderiza el estado actual
//Autor: Rafael S. Martin Gonzalez
//fecha: 09/05/2016

package com.rafaels.simpleandroidgdf;

import com.rafaels.framework.util.InputHandler;
import com.rafaels.framework.util.Painter;
import com.rafaels.game.state.LoadState;
import com.rafaels.game.state.State;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;

public class GameView extends SurfaceView implements Runnable{

	private Bitmap gameImage;
	//Especifica q region de gameImagen se dibujara en pantalla, pantalla completa.
	private Rect gameImageSrc;
	//Especifica el escalado de gameImage en la pantalla.
	private Rect gameImageDst;
	//Es el lienzo donde dibujaremos nuestra gameImagen, pasando por Painter.
	private Canvas gameCanvas;
	private Painter graphics;
	
	private Thread gameThread;
	private volatile boolean running = false;
	private volatile State currentState;
	
	private InputHandler inputHandler;
	
	//Constructor
	public GameView(Context context, int gameWidth, int gameHeight) {
		super(context);
		gameImage = Bitmap.createBitmap(gameWidth, gameHeight, Bitmap.Config.RGB_565);
		gameImageSrc = new Rect(0, 0, gameImage.getWidth(), gameImage.getHeight());
		gameImageDst = new Rect();
		gameCanvas = new Canvas(gameImage);
		graphics = new Painter(gameCanvas);
		
		//Recuperamos el Surfaceholder de nuestra SurfaceView y añadimos una nueva instancia Callback
		//garantizamos el acceso a la superficie de SurfaceView
		SurfaceHolder holder = getHolder();
		holder.addCallback(new Callback(){

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
				// TODO Auto-generated method stub
				
			}
			
			//Llamamos a initImput para crear el surface
			//Nos aseguramos de q currentState sea null antes de crear LoadState
			//asi conservaremos currentState aun cuando la app este en pausa
			//Creamos el hilo del juego
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				//Log.d("GameView", "Surface creado!!");
				initImput();
				if (currentState == null){
					setCurrentState(new LoadState());
				}
				initGame();
			}
			
			//Detenemos el hilo primero
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				//Log.d("GameView", "Surface destruido!!");
				pauseGame();
				
			}
			
		});
	}
	
	//El nuevo contructor a efectos de desarrollo debido a los parametros de entrada
	//Se podria suprimir :-)
	public GameView(Context context){
		super(context);
	}

	public void setCurrentState(State newState) {
		System.gc();
		newState.init();
		currentState = newState;
		inputHandler.setCurrentState(currentState);		
	}
	
	//Vemos si inputHandler es null y lo creamos si es necesario, necesario ya q initImput recibira
	//una llamada cada vez q se cree nuestra superficie.
	//Lo usaremos en surfaceCreate.
	//Establecemos inputHandler como OnTouchListener de GameView	
	private void initImput(){
		if (inputHandler == null){
			inputHandler = new InputHandler();
		}
		setOnTouchListener(inputHandler);
	}

	//Es el bucle del juego
	@Override
	public void run() {
		long updateDurationMillis = 0;
		long sleepDurationMillis = 0;
		
		while (running){
			long beforeUpdateRender = System.nanoTime();
			long deltaMillis = sleepDurationMillis + updateDurationMillis;
			updateAndRender(deltaMillis);
			
			updateDurationMillis = (System.nanoTime() - beforeUpdateRender) / 1000000L;
			sleepDurationMillis = Math.max(2,  17 - updateDurationMillis);		
			
			try{
				Thread.sleep(sleepDurationMillis);				
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	//Pone running en true, crea un hilo y lo ejecuta
	private void initGame(){
		running = true;
		gameThread = new Thread(this, "Hilo del Juego");
		gameThread.start();
	}
	
	//Pone running en false, si el hilo esta activo lo detiene si la app esta en pausa
	//Usaremos este metodo cuando el juego este a punto de pararse, dentro de surfaceDestroy
	private void pauseGame(){
		running = false;
		while (gameThread.isAlive()){
			try{
				gameThread.join();
				break;
			} catch (InterruptedException e){
			}
		}
	}
	
	private void updateAndRender(long delta) {
		currentState.update(delta / 1000f);
		currentState.render(graphics);
		renderGameImage();
	}
	
	private void renderGameImage(){
		//Bloquea el canvas para que el hilo dibuje en el cada vez
		Canvas screen = getHolder().lockCanvas();
		//Verificamos q la pantalla canvas no sea null
		if (screen != null){ 
			//Comprobamos los limites de la pantalla pasandole un Rect (gameImageDst) para obtener los valores
			//left, top, right, button para amoldarlos a los de la pantalla
			screen.getClipBounds(gameImageDst);
			//Dibujamos gameImage en pantalla, usando gameImageSrc para recuperar la imagen completa
			//y usando gameImageDst para escalarla hasta el tamaño disponible.
			screen.drawBitmap(gameImage, gameImageSrc, gameImageDst, null);
			//Desbloquea el canvas y finaliza el dibujo
			getHolder().unlockCanvasAndPost(screen);
		}
	}
	
	public void onResume() {
		// TODO Auto-generated method stub
		
	}

	public void onPause() {
		// TODO Auto-generated method stub
		
	}
}
