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

    public Vidas(int px, int py, int largura, int altura) {
        super(px, py, largura, altura);
    }
    @Override
    public void desenha(Graphics2D g) {

        g.setColor(Color.YELLOW);
        g.drawOval(getPx(), getPy(), getLargura(), getAltura());
        g.fillOval(getPx(), getPy(), getLargura(), getAltura());

    }
}
