//Autor: Rafael S. Martin Gonzalez
//Fecha: 13/08/2016

package com.rafaels.framework.animation;

import com.rafaels.framework.util.Painter;

public class Animation {
	//Matriz de frames
	private Frame[] frames;
	//Matriz secundaria de finalizaciones de frames
	private double[] frameEndTimes;
	//Indice del frame que deberia estar siendo mostrado
	private int currentFrameIndex = 0;
	private double totalDuration = 0;
	//Para determinar currentFrameIndex, cuanto tiempo lleva ejecutandose la animac.
	private double currentTime = 0;
	
	//El constructor acepta un numero indeterminado de argumentos, objetos Frame
	public Animation(Frame... frames){
		this.frames = frames;
		//Creamos una matriz nueva de longitud igual a la matriz de frames
		frameEndTimes = new double[frames.length];
		
		//Vemos el momento final de cada frame y la duracion total
		for (int i = 0; i < frames.length; i++){
			Frame f = frames[i];
			totalDuration += f.getDuration();
			frameEndTimes[i] = totalDuration;
		}
	}
	
	//synchronized asegura q las animaciones se ejecutan con precision en multihilo
	//Recibe el delta desde el bucle del juego, increment, para actualizar currentTime
	//
	public synchronized void update(float increment){
		currentTime += increment;
		//La animacion de ha completado por lo que se repite (no toda %)
		if (currentTime > totalDuration){
			wrapAnimation();
		}
		
		while (currentTime > frameEndTimes[currentFrameIndex]){
			currentFrameIndex++;
		}
	}
	
	//Pone el indice de matriz de animaciones a cero
	//Repite la animacion calculando lo faltante para acabar con totalDuration y
	//hace un nuevo currentTime
	private synchronized void wrapAnimation(){
		currentFrameIndex = 0;
		currentTime %= totalDuration;
	}
	
	//Sobrecarga del metodo render
	//Dibujamos el frame actual de la animacion en las coord x e y
	public synchronized void render(Painter g, int x, int y){
		g.drawImage(frames[currentFrameIndex].getImage(), x, y);
	}
	//Lo mismo de antes pero podemos concretar el tamaño de la animacion
	public synchronized void render(Painter g, int x, int y, int width, int height){
		g.drawImage(frames[currentFrameIndex].getImage(), x, y, width, height);
	}
	
}
