package jogo_pacman;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

import javax.swing.*;

import jogo_pacman.Pizza.Modo;
import jogo_pacman.base.*;


public class JogoCenario extends CenarioPadrao {

	public enum Estado {
		JOGANDO, GANHOU, PERDEU
	}

	public enum Direcao {
		NORTE, SUL, OESTE, LESTE;
	}

	private static final boolean depurar = false;

	private Direcao prxDirecao = Direcao.OESTE;

	private int temporizadorPizza;
	private int temporizadorFantasma;

	private Pizza pizza;

	private Pizza[] inimigos;

	private int[][] grade;

	private Texto texto = new Texto();

	private Random rand = new Random();

	private Estado estado = Estado.JOGANDO;

	public JogoCenario(int largura, int altura) {
		super(largura, altura);
	}

	private ImageIcon fundo;//= new ImageIcon("imagem/pngegg.png"); //TENTAR BOTAR UMA IMAGEM DE FUNDO

	private int largEl;

	private int espLinha = 6; // Espaço grossura linha

	public static final int ESPACO_TOPO = 25; // Espacamento topo

	private int totalPastilha;
	private int pontos;

	private int pontoFugaCol;
	private int pontoFugaLin;

	private int pontoVoltaCol;
	private int pontoVoltaLin;

	private boolean superPizza;
	private Vidas vidas = new Vidas();

	@Override
	public void carregar() {
//		grade = Nivel.cenario; // copiaNivel(Nivel.cenario);
//		copiaNivel(Nivel.cenario); //Copiar o cenario original sem perder nda. Tentar usar para fazer o reset do mapa

		grade = copiaNivel(Nivel.cenario);

		largEl = largura / grade[0].length; // 16px


		texto.setCor(Color.WHITE);

		//pacman
		pizza = new Pizza(0, 0, largEl, largEl); //Criando a bolinha com os parematros, (px,py, altura e largura)
		pizza.setVel(10); // setando a velocidade de movimento do pacman
		pizza.setAtivo(true); //Ativa ou desativa a colisão
		pizza.setCor(Color.YELLOW); //setando a cor do pacman
		pizza.setDirecao(Direcao.OESTE); //nao sei para que serve isso !!

		// Inimigos
		inimigos = new Pizza[4];

		//Vermelho
		inimigos[0] = new Pizza(0, 0, largEl, largEl);
		inimigos[0].setVel(3 + Jogo.nivel);
		inimigos[0].setAtivo(true);
		inimigos[0].setCor(Color.RED);
		inimigos[0].setDirecao(Direcao.OESTE);
		inimigos[0].setModo(Pizza.Modo.CACANDO);

		//Rosa
		inimigos[1] = new Pizza(0, 0, largEl, largEl);
		inimigos[1].setVel(2 + Jogo.nivel);
		inimigos[1].setAtivo(false);
		inimigos[1].setCor(Color.PINK);
		inimigos[1].setDirecao(Direcao.NORTE);
		inimigos[1].setModo(Modo.PRESO);

		//Laranja
		inimigos[2] = new Pizza(0, 0, largEl, largEl);
		inimigos[2].setVel(2 + Jogo.nivel);
		inimigos[2].setAtivo(false);
		inimigos[2].setCor(Color.ORANGE);
		inimigos[2].setDirecao(Direcao.NORTE);
		inimigos[2].setModo(Modo.PRESO);

		//Ciano
		inimigos[3] = new Pizza(0, 0, largEl, largEl);
		inimigos[3].setVel(2 + Jogo.nivel);
		inimigos[3].setAtivo(false);
		inimigos[3].setCor(Color.CYAN);
		inimigos[3].setDirecao(Direcao.NORTE);
		inimigos[3].setModo(Modo.PRESO);

		for (int lin = 0; lin < grade.length; lin++) {
			for (int col = 0; col < grade[0].length; col++) {
				if (grade[lin][col] == Nivel.CN || grade[lin][col] == Nivel.SC) {
					totalPastilha++; //270
				} else if (grade[lin][col] == Nivel.PI) {
					pizza.setPx(converteInidicePosicao(col));
					pizza.setPy(converteInidicePosicao(lin));

				} else if (grade[lin][col] == Nivel.P1) {
					inimigos[0].setPx(converteInidicePosicao(col));
					inimigos[0].setPy(converteInidicePosicao(lin));

				} else if (grade[lin][col] == Nivel.P2) {
					inimigos[1].setPx(converteInidicePosicao(col));
					inimigos[1].setPy(converteInidicePosicao(lin));

				} else if (grade[lin][col] == Nivel.P3) {
					inimigos[2].setPx(converteInidicePosicao(col));
					inimigos[2].setPy(converteInidicePosicao(lin));

				} else if (grade[lin][col] == Nivel.P4) {
					inimigos[3].setPx(converteInidicePosicao(col));
					inimigos[3].setPy(converteInidicePosicao(lin));

				} else if (grade[lin][col] == Nivel.PF) {
					pontoFugaCol = col;
					pontoFugaLin = lin;

				} else if (grade[lin][col] == Nivel.PV) {
					pontoVoltaCol = col;
					pontoVoltaLin = lin;
				}
			}
		}
	}

	public int[][] copiaNivel(int[][] cenario) {

		int[][] temp = new int[cenario.length][cenario[0].length];

		for (int lin = 0; lin < cenario.length; lin++) {
			for (int col = 0; col < cenario[0].length; col++) {
				temp[lin][col] = cenario[lin][col];
			}
		}
		return temp;
	}

	public void reiniciar() {
		superPizza = false;
		temporizadorFantasma = 0;
		prxDirecao = Direcao.OESTE;

		pizza.setDirecao(Direcao.OESTE);

		inimigos[0].setDirecao(Direcao.OESTE);
		inimigos[0].setModo(Pizza.Modo.CACANDO);
		inimigos[0].setAtivo(true);

		inimigos[1].setDirecao(Direcao.NORTE);
		inimigos[1].setModo(Pizza.Modo.PRESO);
		inimigos[1].setAtivo(false);

		inimigos[2].setDirecao(Direcao.NORTE);
		inimigos[2].setModo(Pizza.Modo.PRESO);
		inimigos[2].setAtivo(false);

		inimigos[3].setDirecao(Direcao.NORTE);
		inimigos[3].setModo(Pizza.Modo.PRESO);
		inimigos[3].setAtivo(false);

		for (int lin = 0; lin < grade.length; lin++) {
			for (int col = 0; col < grade[0].length; col++) {
				if (grade[lin][col] == Nivel.PI) {
					pizza.setPx(converteInidicePosicao(col));
					pizza.setPy(converteInidicePosicao(lin));

				} else if (grade[lin][col] == Nivel.P1) {
					inimigos[0].setPx(converteInidicePosicao(col));
					inimigos[0].setPy(converteInidicePosicao(lin));

				} else if (grade[lin][col] == Nivel.P2) {
					inimigos[1].setPx(converteInidicePosicao(col));
					inimigos[1].setPy(converteInidicePosicao(lin));

				} else if (grade[lin][col] == Nivel.P3) {
					inimigos[2].setPx(converteInidicePosicao(col));
					inimigos[2].setPy(converteInidicePosicao(lin));

				} else if (grade[lin][col] == Nivel.P4) {
					inimigos[3].setPx(converteInidicePosicao(col));
					inimigos[3].setPy(converteInidicePosicao(lin));

				}
			}
		}
	}

	@Override
	public void descarregar() { // PARA DE ATUALIZAR O JOGO, FICA EM ESPERA
		pizza = null;
		grade = null;
		inimigos = null;
	}

	@Override
	public void atualizar() {
		if (estado != Estado.JOGANDO) {
			return;
		}

		if (Jogo.controleTecla[Jogo.Tecla.ESQUERDA.ordinal()]) {
			prxDirecao = Direcao.OESTE;

		} else if (Jogo.controleTecla[Jogo.Tecla.DIREITA.ordinal()]) {
			prxDirecao = Direcao.LESTE;

		} else if (Jogo.controleTecla[Jogo.Tecla.CIMA.ordinal()]) {
			prxDirecao = Direcao.NORTE;

		} else if (Jogo.controleTecla[Jogo.Tecla.BAIXO.ordinal()]) {
			prxDirecao = Direcao.SUL;
		}

		pizza.setDirecao(prxDirecao);
		atualizaDirecao(pizza);
		corrigePosicao(pizza);
		comePastilha(pizza);
		pizza.atualiza();

		if (superPizza && temporizadorPizza > 200) {
			temporizadorPizza = 0;
			superPizza(false);

		} else
			temporizadorPizza += 1;

		for (Pizza el : inimigos) {
			if (el == null)
				continue;

			atualizaDirecaoInimigos(el);
			corrigePosicao(el);
			el.atualiza();

			if (Util.colide(pizza, el)) { //PIZZA É REFERENTE AO PACMAN, EL É O INIMIGO
				//isso aqui ta dentro do metodo ATUALIZAR
				if (el.getModo() == Pizza.Modo.CACANDO) {
					//FAZER AQUI O RESET DO JOGO CASO O JOGADOR COMA TODAS AS PASTILHAS ??????? SPOILER: NÃO É AQUI
					if (vidas.getQntVidas() == 0){
						//JOGADOR PERDEU TODAS AS VIDAS, CABOU O JOGO!
						//FAZER AQUI UMA TELA DE GAMEOVER !!!!
						estado = Estado.PERDEU;
						Jogo.setGameOver(true);
						System.out.println(estado);
					} else {
						reiniciar(); // Jogador perdeu vida
						vidas.setQntVidas(vidas.getQntVidas() - 1);
					}

				} else if (el.getModo() == Pizza.Modo.FUGINDO) {
					el.setAtivo(false);
					el.setModo(Pizza.Modo.FANTASMA);
					pontos += 50;
				}
			}
		}
	}


	private boolean validaDirecao(Direcao dir, Pizza el) {

		if (dir == Direcao.OESTE && validaMovimento(el, -1, 0))
			return true;

		else if (dir == Direcao.LESTE && validaMovimento(el, 1, 0))
			return true;

		else if (dir == Direcao.NORTE && validaMovimento(el, 0, -1))
			return true;

		else if (dir == Direcao.SUL && validaMovimento(el, 0, 1))
			return true;

		return false;
	}

	private boolean validaMovimento(Pizza el, int dx, int dy) {
		// Proxima posicao x e y
		int prxPosX = el.getPx() + el.getVel() * dx;
		int prxPosY = el.getPy() + el.getVel() * dy;

		// Coluna e linha
		int col = convertePosicaoIndice(prxPosX);
		int lin = convertePosicaoIndice(prxPosY);

		// Coluna + largura e linha + altura
		int colLarg = convertePosicaoIndice(prxPosX + el.getLargura() - el.getVel());
		int linAlt = convertePosicaoIndice(prxPosY + el.getAltura() - el.getVel());

		if (foraDaGrade(col, lin) || foraDaGrade(colLarg, linAlt))
			return true;

		if (grade[lin][col] == Nivel.BL || grade[lin][colLarg] == Nivel.BL || grade[linAlt][col] == Nivel.BL
				|| grade[linAlt][colLarg] == Nivel.BL) {

			return false;
		}

		// Validar linha branca
		if (el.isAtivo() || el.getModo() == Modo.PRESO) {
			if (grade[lin][col] == Nivel.LN || grade[lin][colLarg] == Nivel.LN || grade[linAlt][col] == Nivel.LN
					|| grade[linAlt][colLarg] == Nivel.LN) {
				return false;
			}
		}

		return true;
	}

	private void atualizaDirecao(Pizza el) {

		if (foraDaTela(el))
			return;

		// Temporario Direcao X e Y
		int tempDx = el.getDx();
		int tempDy = el.getDy();

		Direcao direcao = el.getDirecao();

		if (validaDirecao(direcao, el)) {
			if (direcao == Direcao.OESTE)
				tempDx = -1;
			else if (direcao == Direcao.LESTE)
				tempDx = 1;

			if (direcao == Direcao.NORTE)
				tempDy = -1;
			else if (direcao == Direcao.SUL)
				tempDy = 1;

		}

		if (!validaMovimento(el, tempDx, tempDy))
			tempDx = tempDy = 0;

		el.setDx(tempDx);
		el.setDy(tempDy);
	}

	private void atualizaDirecaoInimigos(Pizza el) {

		if (foraDaTela(el))
			return;

		int col = convertePosicaoIndice(el.getPx());
		int lin = convertePosicaoIndice(el.getPy());

		Direcao direcao = el.getDirecao();

		// Variaveis auxiliares
		Direcao tempDir = null;
		int tempDx = 0, tempDy = 0;
		int xCol = 0, yLin = 0;

		if (el.getModo() == Pizza.Modo.PRESO) {
			if (el.getDirecao() == Direcao.SUL && !validaDirecao(Direcao.SUL, el))
				el.setDirecao(Direcao.NORTE);

			else if (el.getDirecao() == Direcao.NORTE && !validaDirecao(Direcao.NORTE, el))
				el.setDirecao(Direcao.SUL);

			else if (el.getDirecao() != Direcao.NORTE && el.getDirecao() != Direcao.SUL)
				el.setDirecao(Direcao.NORTE);

			if (temporizadorFantasma > 50)
				el.setModo(Pizza.Modo.ATIVO);
			else
				temporizadorFantasma++;

		} else if (el.getModo() == Pizza.Modo.ATIVO) {
			xCol = pontoFugaCol;
			yLin = pontoFugaLin;

			int colLarg = convertePosicaoIndice(el.getPx() + el.getLargura() - el.getVel());
			int linAlt = convertePosicaoIndice(el.getPy() + el.getAltura() - el.getVel());

			if (lin > yLin && validaDirecao(Direcao.NORTE, el))
				el.setDirecao(Direcao.NORTE);

			else if (lin < yLin && validaDirecao(Direcao.SUL, el))
				el.setDirecao(Direcao.SUL);

			else if (col < xCol && validaDirecao(Direcao.LESTE, el))
				el.setDirecao(Direcao.LESTE);

			else if (col > xCol && validaDirecao(Direcao.OESTE, el))
				el.setDirecao(Direcao.OESTE);

			else if (col == xCol && lin == yLin && colLarg == xCol && linAlt == yLin) {
				el.setAtivo(true);
				el.setModo(Pizza.Modo.CACANDO);
			}

		} else if (el.getModo() == Pizza.Modo.CACANDO || el.getModo() == Pizza.Modo.FUGINDO) {

			xCol = convertePosicaoIndice(pizza.getPx());
			yLin = convertePosicaoIndice(pizza.getPy());

			// Inverte posicao para fugir
			if (el.getModo() == Pizza.Modo.FUGINDO) {
				xCol = xCol * -1;
				yLin = yLin * -1;
			}

			// TODO melhorar, problema de leg
			boolean perdido = rand.nextInt(100) == 35;

			if (el.isAtivo() && perdido) {
				tempDir = sorteiaDirecao();

			} else if (direcao == null) {
				direcao = sorteiaDirecao();

			} else if (direcao == Direcao.NORTE || direcao == Direcao.SUL) {
				if (xCol < col && validaDirecao(Direcao.OESTE, el))
					tempDir = Direcao.OESTE;
				else if (xCol > col && validaDirecao(Direcao.LESTE, el))
					tempDir = Direcao.LESTE;

			} else {
				/* direcao = OESTE ou LESTE */
				if (yLin < lin && validaDirecao(Direcao.NORTE, el))
					tempDir = Direcao.NORTE;
				else if (yLin > lin && validaDirecao(Direcao.SUL, el))
					tempDir = Direcao.SUL;
			}

			if (tempDir != null && validaDirecao(tempDir, el))
				el.setDirecao(tempDir);
			else if (!validaDirecao(el.getDirecao(), el))
				el.setDirecao(sorteiaDirecao());

		} else if (el.getModo() == Pizza.Modo.FANTASMA) {
			xCol = pontoFugaCol;
			yLin = pontoFugaLin;

			if (direcao == Direcao.NORTE || direcao == Direcao.SUL) {
				if (xCol < col && validaDirecao(Direcao.OESTE, el))
					tempDir = Direcao.OESTE;
				else if (xCol > col && validaDirecao(Direcao.LESTE, el))
					tempDir = Direcao.LESTE;

			} else {
				if (yLin < lin && validaDirecao(Direcao.NORTE, el))
					tempDir = Direcao.NORTE;
				else if (yLin > lin && validaDirecao(Direcao.SUL, el))
					tempDir = Direcao.SUL;
			}

			if (tempDir != null && validaDirecao(tempDir, el))
				el.setDirecao(tempDir);
			else if (!validaDirecao(el.getDirecao(), el))
				el.setDirecao(trocaDirecao(el.getDirecao()));

			if (col == xCol && lin == yLin)
				el.setModo(Pizza.Modo.INATIVO);

		} else if (el.getModo() == Pizza.Modo.INATIVO) {
			xCol = pontoVoltaCol;
			yLin = pontoVoltaLin;

			if (lin > yLin && validaDirecao(Direcao.NORTE, el))
				el.setDirecao(Direcao.NORTE);

			else if (lin < yLin && validaDirecao(Direcao.SUL, el))
				el.setDirecao(Direcao.SUL);

			else if (col < xCol && validaDirecao(Direcao.LESTE, el))
				el.setDirecao(Direcao.LESTE);

			else if (col > xCol && validaDirecao(Direcao.OESTE, el))
				el.setDirecao(Direcao.OESTE);

			else if (col == xCol && lin == yLin)
				el.setModo(Pizza.Modo.PRESO);
		}

		if (validaDirecao(el.getDirecao(), el)) {
			if (el.getDirecao() == Direcao.NORTE)
				tempDy = -1;
			else if (el.getDirecao() == Direcao.SUL)
				tempDy = 1;
			else if (el.getDirecao() == Direcao.OESTE)
				tempDx = -1;
			else if (el.getDirecao() == Direcao.LESTE)
				tempDx = 1;
		}

		el.setDx(tempDx);
		el.setDy(tempDy);
	}

	private Direcao trocaDirecao(Direcao direcao) {
		if (direcao == Direcao.NORTE)
			return Direcao.OESTE;
		else if (direcao == Direcao.OESTE)
			return Direcao.SUL;
		else if (direcao == Direcao.SUL)
			return Direcao.LESTE;
		else
			return Direcao.NORTE;
	}

	private Direcao sorteiaDirecao() {
		return Direcao.values()[rand.nextInt(Direcao.values().length)];
	}

	private boolean foraDaGrade(int coluna, int linha) {
		if (linha < 0 || linha >= grade.length)
			return true;

		if (coluna < 0 || coluna >= grade[0].length)
			return true;

		return false;
	}

	private boolean foraDaTela(Elemento el) {
		if (el.getPx() < 0 || el.getPx() + el.getLargura() > largura)
			return true;

		if (el.getPy() < 0 || el.getPy() + el.getAltura() > altura)
			return true;

		return false;
	}

	/**
	 * Teleporte
	 */
	private void corrigePosicao(Pizza el) {
		int novaPx = el.getPx(); // Nova posi��o x
		int novaPy = el.getPy(); // Nova posi��o y

		int col = convertePosicaoIndice(el.getPx()) * largEl;
		int lin = convertePosicaoIndice(el.getPy()) * largEl;

		if (el.getDx() == 0 && novaPx != col)
			novaPx = col;
		else if (el.getPx() + largEl < 0)
			novaPx = largura;
		else if (el.getPx() > largura)
			novaPx = -largEl;

		if (el.getDy() == 0 && novaPy != lin)
			novaPy = lin;
		else if (el.getPy() + largEl < 0)
			novaPy = altura;
		else if (el.getPy() > altura)
			novaPy = -largEl;

		el.setPx(novaPx);
		el.setPy(novaPy);
	}

	public void reiniciarComVitoria() { //tirar isso aqui depois
		superPizza = false;
		temporizadorFantasma = 0;
		totalPastilha = 0;

		prxDirecao = Direcao.OESTE;

		pizza.setDirecao(Direcao.OESTE);

		inimigos[0].setDirecao(Direcao.OESTE);
		inimigos[0].setModo(Pizza.Modo.CACANDO);
		inimigos[0].setAtivo(true);

		inimigos[1].setDirecao(Direcao.NORTE);
		inimigos[1].setModo(Pizza.Modo.PRESO);
		inimigos[1].setAtivo(false);

		inimigos[2].setDirecao(Direcao.NORTE);
		inimigos[2].setModo(Pizza.Modo.PRESO);
		inimigos[2].setAtivo(false);

		inimigos[3].setDirecao(Direcao.NORTE);
		inimigos[3].setModo(Pizza.Modo.PRESO);
		inimigos[3].setAtivo(false);
		grade = copiaNivel(Nivel.cenario);

		for (int lin = 0; lin < grade.length; lin++) {
			for (int col = 0; col < grade[0].length; col++) {
				if (grade[lin][col] == Nivel.CN || grade[lin][col] == Nivel.SC) {
					totalPastilha++; //270
				} else if (grade[lin][col] == Nivel.PI) {
					pizza.setPx(converteInidicePosicao(col));
					pizza.setPy(converteInidicePosicao(lin));

				} else if (grade[lin][col] == Nivel.P1) {
					inimigos[0].setPx(converteInidicePosicao(col));
					inimigos[0].setPy(converteInidicePosicao(lin));

				} else if (grade[lin][col] == Nivel.P2) {
					inimigos[1].setPx(converteInidicePosicao(col));
					inimigos[1].setPy(converteInidicePosicao(lin));

				} else if (grade[lin][col] == Nivel.P3) {
					inimigos[2].setPx(converteInidicePosicao(col));
					inimigos[2].setPy(converteInidicePosicao(lin));

				} else if (grade[lin][col] == Nivel.P4) {
					inimigos[3].setPx(converteInidicePosicao(col));
					inimigos[3].setPy(converteInidicePosicao(lin));

				} else if (grade[lin][col] == Nivel.PF) {
					pontoFugaCol = col;
					pontoFugaLin = lin;

				} else if (grade[lin][col] == Nivel.PV) {
					pontoVoltaCol = col;
					pontoVoltaLin = lin;
				}
			}
		}
	}

	private void comePastilha(Elemento el) {
		int col = convertePosicaoIndice(el.getPx());
		int lin = convertePosicaoIndice(el.getPy());

		if (foraDaGrade(col, lin)) {
			return;
		}

		if (grade[lin][col] == Nivel.CN || grade[lin][col] == Nivel.SC) {
			pontos += grade[lin][col] == Nivel.CN ? 5 : 25;
			totalPastilha--;

			if (totalPastilha == 0) {
				estado = Estado.JOGANDO;
				reiniciarComVitoria();
			}

			if (grade[lin][col] == Nivel.SC){
				superPizza(true);
			}

			grade[lin][col] = Nivel.EV;
		}
	}

	private void superPizza(boolean modoSuper) {
		superPizza = modoSuper;
		temporizadorPizza = 0;

		for (Pizza el : inimigos) {
			if (el == null)
				continue;

			if (modoSuper && el.getModo() == Pizza.Modo.CACANDO)
				el.setModo(Pizza.Modo.FUGINDO);
			else if (!modoSuper && el.getModo() == Pizza.Modo.FUGINDO)
				el.setModo(Pizza.Modo.CACANDO);
		}
	}

	private int converteInidicePosicao(int linhaColuna) {
		return linhaColuna * largEl;
	}

	private int convertePosicaoIndice(int eixoXY) {
		return eixoXY / largEl;
	}

	protected void imprimeMovimento(int x, int y, int ix, int iy, int dx, int dy) {

		int px, py;

		px = x + ix * dx;
		py = y + iy * dy;

		int col = convertePosicaoIndice(px);
		int lin = convertePosicaoIndice(py);

		int col2 = convertePosicaoIndice(px + 14);
		int lin2 = convertePosicaoIndice(py + 14);

		System.out.print("[x=" + x + ", y=" + y + ", ix=" + ix + ", iy=" + iy + ", dx=" + dx + ", dy=" + dy + "]");
		System.out.print("[lin=" + lin + ", col=" + col + "]");
		System.out.println("[lin2=" + lin2 + ", col2=" + col2 + "]");

	}

	@Override
	public void desenhar(Graphics2D g) {

		if (fundo != null) {
			//g.drawImage(fundo.getImage(), 0, ESPACO_TOPO, null);
		} else {
			for (int lin = 0; lin < grade.length; lin++) {
				for (int col = 0; col < grade[0].length; col++) {
					int valor = grade[lin][col];

					if (valor == Nivel.BL) {
						g.setColor(superPizza ? Color.DARK_GRAY : Color.BLUE);
						g.fillRect(col * largEl, lin * largEl + ESPACO_TOPO, largEl, largEl);

					}else if (valor == Nivel.CN) {
						g.setColor(Color.WHITE);
						g.fillRect(col * largEl + espLinha, lin * largEl + espLinha + ESPACO_TOPO, largEl - espLinha * 2, largEl - espLinha * 2);

					}else if (valor == Nivel.SC) {
						g.setColor(Color.YELLOW);
						g.fillRect(col * largEl + espLinha / 2, lin * largEl + espLinha / 2 + ESPACO_TOPO, largEl - espLinha, largEl - espLinha);

					} else if (valor == Nivel.LN) {
						g.setColor(Color.WHITE);
						g.fillRect(col * largEl, lin * largEl + espLinha + ESPACO_TOPO, largEl, largEl - espLinha * 2);
					}
				}
			}
		}

		texto.desenha(g, "Pontos: " + pontos, 10, 20); //escrevendo os pontos na tela. parte de cima
		texto.desenha(g, "Para pausar o jogo pressione Enter", 135, 540);

		//FAZER AQUI A LOGICA DE SUMIR COM AS VIDAS NA TELA !!!!

		if (vidas.getQntVidas() == 3) {
			vidas.desenha(g, 10, 527,  16, 16); //mais a esquerda - terceira
			vidas.desenha(g, 30, 527, 16, 16); //do meio - segunda
			vidas.desenha(g, 50, 527, 16, 16); //mais a direita - primeira
		} else if (vidas.getQntVidas() == 2) {
			vidas.desenha(g, 10, 527,  16, 16); //mais a esquerda - terceira
			vidas.desenha(g, 30, 527, 16, 16); //do meio - segunda
		} else if (vidas.getQntVidas() == 1) {
			vidas.desenha(g, 10, 527,  16, 16); //mais a esquerda - terceira
		}

		pizza.desenha(g);

		for (Elemento el : inimigos) {
			if (el == null)
				continue;

			el.desenha(g);
		}

		if (depurar) {
			g.setColor(Color.WHITE);
			for (int i = 0; i < grade.length; i++) {
				for (int j = 0; j < grade[0].length; j++)
					g.drawRect(j * largEl, i * largEl + ESPACO_TOPO, largEl, largEl);

			}

			int col = convertePosicaoIndice(pizza.getPx());
			int lin = convertePosicaoIndice(pizza.getPy());

			g.setColor(Color.ORANGE);
			g.fillRect(col * 16, lin * 16 + ESPACO_TOPO, 16, 16);
		}

	}

}
