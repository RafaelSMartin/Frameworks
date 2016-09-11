//Autor: Rafael S. Martin Gonzalez
//Fecha: 13/08/2016

package com.rafaels.game.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.rafaels.framework.util.InputHandler;
import com.rafaels.game.state.LoadState;
import com.rafaels.game.state.State;

@SuppressWarnings("serial")

public class Game extends JPanel implements Runnable{
	
	private int gameWidth;
	private int gameHeight;	
	private Image gameImage;
	
	private Thread gameThread;
	private volatile boolean running;	
	private volatile State currentState;
	
	private InputHandler inputHandler;
	
	public Game(int gameWidth, int gameHeight){
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		setPreferredSize(new Dimension(gameWidth, gameHeight));
		setBackground(Color.BLACK);
		setFocusable(true);
		requestFocus();		
	}

	public void setCurrentState(State newState){
		System.gc();
		newState.init();
		currentState = newState;		
		inputHandler.setCurrentState(currentState);
	}
	
	@Override
	public void addNotify(){
		// invoca al metodo addNotify de la clase padre JPanel, por eso tb @Override
		super.addNotify();
		initInput();
		setCurrentState(new LoadState());
		initGame();
	}
	
	private void initInput(){
		inputHandler = new InputHandler();
		addKeyListener(inputHandler);
		addMouseListener(inputHandler);		
	}
	
	private void initGame(){
		running = true;
		gameThread = new Thread(this, "Hilo Game");
		gameThread.start();
	}
	
	@Override
	public void run() {
		
		// La suma de las variables debera ser 17 en cada iteracion
		// Mide las actualizaciones y el renderizado
		long updateDurationMillis = 0;
		// Mide la latencia
		long sleepDurationMillis = 0;
		
		
		while(running){
			long beforeUpdateRender = System.nanoTime();
			// Sera 17 a no ser q updateDurationMillis tardase mucho
			long deltaMillis = updateDurationMillis + sleepDurationMillis;
			
			updateAndRender(deltaMillis);
			
			updateDurationMillis = (System.nanoTime() - beforeUpdateRender) / 1000000L;
			sleepDurationMillis = Math.max(2, 17 - updateDurationMillis);
			
			try {				
				Thread.sleep(sleepDurationMillis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//El juego finaliza cuando la condicion del while es falso
		System.exit(0);		
	}

	private void updateAndRender(long deltaMillis) {
		currentState.update(deltaMillis / 1000f);
		prepareGameImage();
		currentState.render(gameImage.getGraphics());
		//Le pasamos el objeto de getGraphics (es un obj JPanel) a renderGameImage 
		renderGameImage(getGraphics());
	}
	
	private void prepareGameImage(){
		if(gameImage == null)
			gameImage = createImage(gameWidth, gameHeight);
		
		Graphics g = gameImage.getGraphics();
		g.fillRect(0, 0, gameWidth, gameHeight);		
	}
	
	public void exit(){
		running = false;
	}
	// Renderizado activo, se hace siempre llamadas al render	
	private void renderGameImage(Graphics g){
		if (gameImage != null){
			g.drawImage(gameImage, 0, 0, null);
		}
		// Nos deshacemos del objeto g tipo Graphics
		g.dispose();
	}
}
