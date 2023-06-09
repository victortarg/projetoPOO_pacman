package jogo_pacman.base;

import java.awt.Graphics2D;

public abstract class CenarioPadrao {
	protected int altura, largura, pontos;

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public CenarioPadrao(int largura, int altura) {
		this.altura = altura;
		this.largura = largura;
	}

	public abstract void carregar();

	public abstract void descarregar();

	public abstract void atualizar();

	public abstract void desenhar(Graphics2D g);

}
