package jogo_pacman.base;

import jogo_pacman.JogoCenario;

import java.awt.*;

public class Vidas extends Elemento{
    private int qntVidas = 3;

    public int getQntVidas() {
        return qntVidas;
    }

    public void setQntVidas(int qntVidas) {
        this.qntVidas = qntVidas;
    }

    public Vidas() {

    }

    public void desenha(Graphics2D g, int px, int py, int largura, int altura) {

        g.setColor(Color.YELLOW);
        g.drawOval(px, py, largura, altura);
        g.fillOval(px, py, largura, altura);

    }
}
