package jogo_pacman;

import java.awt.*;

import jogo_pacman.base.CenarioPadrao;
import jogo_pacman.base.Menu;
import jogo_pacman.base.Util;

public class InicioCenario extends CenarioPadrao {

	public InicioCenario(int largura, int altura) {
		super(largura, altura);
	}

	private Menu menuJogo;

	@Override
	public void carregar() {

		menuJogo = new Menu("Dificuldade");
		menuJogo.addOpcoes("Facil", "Normal", "Dificil");
		
		Util.centraliza(menuJogo, largura, altura);

		menuJogo.setPy(menuJogo.getPy() + menuJogo.getAltura());

		menuJogo.setAtivo(true);
		menuJogo.setSelecionado(true);
	}

	@Override
	public void descarregar() {
		Jogo.nivel = menuJogo.getOpcaoId();
	}

	@Override
	public void atualizar() {
		if (Jogo.controleTecla[Jogo.Tecla.CIMA.ordinal()] || Jogo.controleTecla[Jogo.Tecla.BAIXO.ordinal()]) {

		} else if (Jogo.controleTecla[Jogo.Tecla.ESQUERDA.ordinal()] || Jogo.controleTecla[Jogo.Tecla.DIREITA.ordinal()]) {
			menuJogo.setTrocaOpcao(Jogo.controleTecla[Jogo.Tecla.ESQUERDA.ordinal()]);

		}

		Jogo.liberaTeclas();

	}

	@Override
	public void desenhar(Graphics2D g) {
		menuJogo.desenha(g);
	}
}
