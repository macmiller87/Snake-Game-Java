package main;

import javax.swing.JFrame;

public class Game extends JFrame {
	
	// Construtor que tem a instância da classe Graficos, e os parâmetros de title do game, juntamente com algumas definicões do JFrame.
	public Game() {
		this.setTitle("Snake-Game");
		this.add(new Graficos()); 
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		
	}

}
