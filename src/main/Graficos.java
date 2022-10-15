package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Graficos extends JPanel implements ActionListener {
	
	// Parâmetros painel jogo.
	static final int WIDTH = 780;
	static final int HEIGHT = 780;
	static final int TICK_SIZE = 40;
	static final int BOARD_SIZE = (WIDTH * HEIGHT) / (TICK_SIZE * TICK_SIZE);
	
	// Parâmetros para apresentação do texto em tela.
	final Font font = new Font("Fira Code", Font.BOLD, 20);
	
	// Parâmetros que definem tamanho da cobra em relação a tela.
	int snakePosX[] = new int[BOARD_SIZE];
	int snakePosY[] = new int[BOARD_SIZE];
	int snakeLenght;
	
	// Chamada direta da classe Comida, com var que grava o placar e frutas.
	Comida food;
	int score;
	int foodEaten;
	
	// Parâmetros de direção inicial, timer e boolean de movimentação.
	char direction = 'R';
	boolean isMoving = false;
	final Timer timer = new Timer(140, this);
	
	// Construtor com parâmetros de definição de cor tamanho, e teclas.
	public Graficos() {
		
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.GREEN);
		this.setFocusable(true);
		
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				
				if(isMoving) {
					
					switch(e.getKeyCode()) {
					
						case KeyEvent.VK_LEFT:
							
							if(direction != 'R') {
								direction = 'L';
							}
							break;
							
						case KeyEvent.VK_RIGHT:

							if(direction != 'L') {
								direction = 'R';
							}
							break;
							
						case KeyEvent.VK_UP:
							
							if(direction != 'D') {
								direction = 'U';
							}
							break;	
							
						case KeyEvent.VK_DOWN:
							
							if(direction != 'U') {
								direction = 'D';
							}
							break;
					}
					
				}else {
					start();
				}			
			}
		});
		
		start();
	}
	
	// Construtor com parâmetros para iniciar o jogo.
	protected void start() {
		
		snakePosX = new int[BOARD_SIZE];
		snakePosY = new int[BOARD_SIZE];
		snakeLenght = 2;
		score = 0;
		foodEaten = 0;
		direction = 'R';
		isMoving = true;
		spawnFood();
		timer.start();
		
	}
	
	// Construtor que tem parâmetros que gera a comida, verifica se houve colisisão, define placar e texto final.
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		String scoreText = String.format("Score: %d", score);
		String scoreText2 = String.format("Fruit: %d", foodEaten);
		g.setFont(font);
		g.drawString(scoreText, (WIDTH - getFontMetrics(g.getFont()).stringWidth(scoreText)) / 20, HEIGHT / 10);
		g.drawString(scoreText2, (WIDTH - getFontMetrics(g.getFont()).stringWidth(scoreText2)) / 20, HEIGHT / 8);
		
		if(isMoving) {
			
			g.setColor(Color.RED);
			g.fillOval(food.getPosX(), food.getPosY(), TICK_SIZE, TICK_SIZE);
			g.setColor(Color.DARK_GRAY);
			
			for(int i = 0; i < snakeLenght; i++) {
				g.fillRect(snakePosX[i], snakePosY[i], TICK_SIZE, TICK_SIZE);
			}
			
		}else {
			
			String scoreText3 = String.format("Control Menu");
			String scoreText4 = String.format("Press ↑ to Up.");
			String scoreText5 = String.format("Press ↓ to Down.");
			String scoreText6 = String.format("Press ← to Left.");
			String scoreText7 = String.format("Press → to Right.");
			String scoreText8 = String.format("The End .... Press any key to play again!" + "\n");
			g.setColor(Color.black);
			g.setFont(font);
			g.drawString(scoreText3, (160 - getFontMetrics(g.getFont()).stringWidth(scoreText3)), 540);
			g.drawString(scoreText4, (180 - getFontMetrics(g.getFont()).stringWidth(scoreText4)), 580);
			g.drawString(scoreText5, (204 - getFontMetrics(g.getFont()).stringWidth(scoreText5)), 600);
			g.drawString(scoreText6, (204 - getFontMetrics(g.getFont()).stringWidth(scoreText6)), 620);
			g.drawString(scoreText7, (216 - getFontMetrics(g.getFont()).stringWidth(scoreText7)), 640);
			g.drawString(scoreText8, (WIDTH - getFontMetrics(g.getFont()).stringWidth(scoreText8)) / 2, HEIGHT / 2);
			
		}
		
	}
	
	// Construtor que tem parâmetros que definem os movimentos da cobra.
	protected void moving() {
		
		for(int i = snakeLenght; i > 0; i--) {
			snakePosX[i] = snakePosX[i - 1];
			snakePosY[i] = snakePosY[i - 1];
		}
		
		switch(direction) {
			case 'U' -> snakePosY[0] -= TICK_SIZE;
			case 'D' -> snakePosY[0] += TICK_SIZE;
			case 'L' -> snakePosX[0] -= TICK_SIZE;
			case 'R' -> snakePosX[0] += TICK_SIZE;
		}
		
	}

	// Construtor que retorna o valor do placar.
	protected int foodEaten() {
		return score;
	};
	
	// Construtor que que retorna o quantidade de frutas comidas.
	protected int foodScore() {
		return foodEaten;
	};
	
	// Construtor que instância a classe Comida, com suas definições.
	protected void spawnFood() {
		food = new Comida();	
	}
	
	// Construtor que seta os parâmetros, quando a cobra come a comida, seta a quantidade de  comida o placar, o tamanho da cobra, e gera mais comida.
	protected void eatFood() {
		
		if((snakePosX[0] == food.getPosX()) && (snakePosY[0] == food.getPosY())) {
			
			snakeLenght++;
			score += 10;
			foodEaten++;
			spawnFood();
			
		}
		
	}
	
	// Construtor que verifica a colisão da cobra.
	protected void collisionTest() {
		
		for(int i = snakeLenght; i > 0; i--) {
			
			if((snakePosX[0] == snakePosX[i]) && (snakePosY[0] == snakePosY[i])) {
				
				isMoving = false;
				break;
				
			}
			
		}
		
		if(snakePosX[0] < 0 || (snakePosX[0] > WIDTH - TICK_SIZE) || snakePosY[0] < 0 ||(snakePosY[0] > HEIGHT - TICK_SIZE)) {
			isMoving = false;
		}
		
		if(!isMoving) {
			timer.stop();
		}
		
	}
	
	// Construtor que invoca/chama alguns construtores acima. 
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(isMoving) {
			
			moving();
			collisionTest();
			eatFood();
			
		}
		
		repaint();
	}

}
