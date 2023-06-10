package jogo_pacman.base;

import javax.swing.*;
import java.awt.*;

public class Menu extends Texto {

	private short idx;
	private String rotulo;
	private String[] opcoes;
	private boolean selecionado;
	private final ImageIcon imagemMenu = new ImageIcon("C:\\Users\\victo\\CodigoFontes\\CodigosFontes\\Java\\pac_manOriginal\\src\\imagem\\pngegg.png");
	private final ImageIcon imagemPressEnter = new ImageIcon("C:\\Users\\victo\\CodigoFontes\\CodigosFontes\\Java\\pac_manOriginal\\src\\imagem\\pressEnter.png");

	public Menu(String rotulo) {
		super();

		this.rotulo = rotulo;

		setLargura(120);
		setAltura(20);
		setCor(Color.CYAN);
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
		g.drawImage(imagemMenu.getImage(), 40, 10, null);
		g.drawImage(imagemPressEnter.getImage(), getPx() - 10, 400, 150, 50,  null);
		super.desenha(g, String.format("%s:\n [%s]", getRotulo(), opcoes[idx]), getPx() - 30, getPy() + getAltura());

		if (selecionado) {
			g.drawLine(getPx() - 35, getPy(), getPx() - 35, getPy() + getAltura() + 10);
			g.drawLine(getPx() + getLargura() + 50, getPy(), getPx() + getLargura() + 50, getPy() + getAltura() + 10);
			g.drawLine(getPx() - 35, getPy() + getAltura() + 10, getPx() + getLargura() + 50, getPy() + getAltura() + 10);
			g.drawLine(getPx() - 35, getPy() + getAltura() - 20, getPx() + getLargura() + 50, getPy() + getAltura() - 20);
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
