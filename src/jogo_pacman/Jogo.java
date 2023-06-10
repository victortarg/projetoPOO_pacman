package jogo_pacman;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.*;

import jogo_pacman.base.CenarioPadrao;
import jogo_pacman.JogoCenario;
import jogo_pacman.base.Vidas;

public class Jogo extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final int FPS = 1000 / 20;

	private static final int JANELA_ALTURA = 550;
	private static final int JANELA_LARGURA = 448;

	public static int getJANELA_ALTURA() {
		return JANELA_ALTURA;
	}

	public static int getJANELA_LARGURA() {
		return JANELA_LARGURA;
	}

	private JPanel tela;

	private Graphics2D g2d;

	private BufferedImage buffer;

	private CenarioPadrao cenario;
	private final ImageIcon imagemPause = new ImageIcon("C:\\Users\\victo\\CodigoFontes\\CodigosFontes\\Java\\pac_manOriginal\\src\\imagem\\pngegg.png");
	private Vidas vidas;
	private static boolean gameOver = false;

	public static boolean isGameOver() {
		return gameOver;
	}

	public static void setGameOver(boolean gameOver) {
		Jogo.gameOver = gameOver;
	}

	public enum Tecla {
		CIMA, BAIXO, ESQUERDA, DIREITA, BA, BB, BC
	}

	public static boolean[] controleTecla = new boolean[Tecla.values().length];

	public static void liberaTeclas() {
		for (int i = 0; i < controleTecla.length; i++) {
			controleTecla[i] = false;
		}
	}

	private void setaTecla(int tecla, boolean pressionada) {
		switch (tecla) {
		case KeyEvent.VK_UP:
			controleTecla[Tecla.CIMA.ordinal()] = pressionada;
			break;
		case KeyEvent.VK_DOWN:
			controleTecla[Tecla.BAIXO.ordinal()] = pressionada;
			break;
		case KeyEvent.VK_LEFT:
			controleTecla[Tecla.ESQUERDA.ordinal()] = pressionada;
			break;
		case KeyEvent.VK_RIGHT:
			controleTecla[Tecla.DIREITA.ordinal()] = pressionada;
			break;

		case KeyEvent.VK_ESCAPE:
			controleTecla[Tecla.BB.ordinal()] = pressionada;
			break;

		case KeyEvent.VK_SPACE:
			controleTecla[Tecla.BC.ordinal()] = pressionada;
			break;

		case KeyEvent.VK_ENTER:
			controleTecla[Tecla.BA.ordinal()] = pressionada;
		}
	}

	public static int nivel;

	public static boolean pausado;

	public Jogo() {
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				setaTecla(e.getKeyCode(), false);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				setaTecla(e.getKeyCode(), true);
			}
		});

		buffer = new BufferedImage(JANELA_LARGURA, JANELA_ALTURA, BufferedImage.TYPE_INT_RGB);

		g2d = buffer.createGraphics();

		tela = new JPanel() { // painel principal
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(buffer, 0, 0, null); // vai desenhar meu jogo
			}

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(JANELA_LARGURA, JANELA_ALTURA);
			}

			@Override
			public Dimension getMinimumSize() {
				return getPreferredSize();
			}
		};

		getContentPane().add(tela);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setVisible(true);

		tela.repaint();

	}

	private void carregarJogo() {
		cenario = new InicioCenario(tela.getWidth(), tela.getHeight());
		cenario.carregar();
	}

	public void iniciarJogo() {
		long prxAtualizacao = 0;

		while (true) {
			if (System.currentTimeMillis() >= prxAtualizacao) {

				g2d.setColor(Color.BLACK); //MUDA A COR DO FUNDO (DEIXAR PRETO)
				g2d.fillRect(0, 0, JANELA_LARGURA, JANELA_ALTURA);

				if (controleTecla[Tecla.BA.ordinal()]) {
					// Pressionou Enter, VAI INICIAR O JOGO, FAZ A TROCA DE TELA.
					if (cenario instanceof InicioCenario) {
						cenario.descarregar();
						cenario = null;
						cenario = new JogoCenario(tela.getWidth(), tela.getHeight());

						g2d.setColor(Color.WHITE);
						g2d.drawString("Carregando...", 20, 20);
						tela.repaint();

						cenario.carregar();

					} else {
						Jogo.pausado = !Jogo.pausado;

					}


					liberaTeclas();

				} else if (controleTecla[Tecla.BB.ordinal()]) {
					// Pressionou ESQ
					if (!(cenario instanceof InicioCenario)) { //LOGICA QUE FAZ COM QUE O CENARIO SUMA DEPOIS DE APERTAR ESQ

						cenario.descarregar();
						cenario = null;

						cenario = new InicioCenario(tela.getWidth(), tela.getHeight());
						cenario.carregar();
						gameOver = false;
					}

					liberaTeclas();

				}
				if (cenario == null) {
					g2d.setColor(Color.WHITE);
					g2d.drawString("Carregando...", 20, 20);

				} else {

					if (!Jogo.pausado) { // executa depois de despausar o jogo
						cenario.atualizar();
						cenario.desenhar(g2d); //chama a função desenha. Que desenha o cenario
					}

					if (Jogo.pausado) { // tela de pause
						g2d.setColor(Color.CYAN);
						g2d.drawString("Jogo Pausado", tela.getWidth() / 2 - 50, tela.getHeight() / 2);
						g2d.drawImage(imagemPause.getImage(),40, 10, null);
					}
				}

				if (gameOver) {
					g2d.setColor(Color.BLACK); //MUDA A COR DO FUNDO (DEIXAR PRETO)
					g2d.fillRect(0, 0, JANELA_LARGURA, JANELA_ALTURA);
				}

				tela.repaint();
				prxAtualizacao = System.currentTimeMillis() + FPS;
			}
		}
	}


	public static void main(String[] args) {
		Jogo jogo = new Jogo();
		jogo.carregarJogo();
		jogo.iniciarJogo();
	}

}
