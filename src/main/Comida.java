package main;

import java.util.Random;

public class Comida {
	
	private final int posX;
	private final int posY;
	
	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}
	
	// Construtor que seta os parâmetos de posição da comida.
	public Comida() {
		
		posX = generatePos(Graficos.WIDTH);
		posY = generatePos(Graficos.HEIGHT);
		
	}
	
	// Construtor que gera randomicamente a comida na tela, através dos parâmetros.
	private int generatePos(int size) {
		
		Random random = new Random();
		return random.nextInt(size / Graficos.TICK_SIZE) * Graficos.TICK_SIZE;
		
	}
	
}
