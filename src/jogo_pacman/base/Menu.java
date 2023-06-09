package jogo_pacman.base;

import java.awt.*;

public class Menu extends Texto {

	private short idx;
	private String rotulo;
	private Image fundo;
	private String[] opcoes;
	private boolean selecionado;

	public Menu(String rotulo) {
		super();

		this.rotulo = rotulo;

		setLargura(120);
		setAltura(20);
		setCor(Color.WHITE);
	}

	public void addOpcoes(String... opcao) {
		opcoes = opcao;
	}

	@Override
	public void desenha(Graphics2D g) { //Mudar o menu com essa função aqui.
		if (opcoes == null) {
			return;
		}

		g.setColor(getCor());
		super.desenha(g, String.format("%s: [%s]", getRotulo(), opcoes[idx]), getPx(), getPy() + getAltura()); //IMPORTANTE



		if (selecionado) {
			g.drawLine(getPx(), getPy() + getAltura() + 10, getPx() + getLargura() + 75, getPy() + getAltura() + 10);

		}

	}

	public String getRotulo() {
		return rotulo;
	}

	public void setRotulo(String rotulo) {
		this.rotulo = rotulo;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;

	}

	public int getOpcaoId() {
		return idx;
	}

	public String getOpcaoTexto() {
		return opcoes[idx];
	}

	public void setTrocaOpcao(boolean esquerda) {
		if (!isSelecionado() || !isAtivo())
			return;

		idx += esquerda ? -1 : 1;

		if (idx < 0)
			idx = (short) (opcoes.length - 1);
		else if (idx == opcoes.length)
			idx = 0;

	}

}
