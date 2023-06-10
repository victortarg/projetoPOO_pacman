package jogo_pacman;

import jogo_pacman.base.CenarioPadrao;
import jogo_pacman.base.Menu;
import jogo_pacman.base.MenuFinal;
import jogo_pacman.base.Util;

import java.awt.*;

public class FinalCenario extends CenarioPadrao {
    public FinalCenario(int largura, int altura) {
        super(largura, altura);
    }

    private MenuFinal menuGameOver;

    @Override
    public void carregar() {
        menuGameOver = new MenuFinal();

        Util.centraliza(menuGameOver, largura, altura);

        menuGameOver.setPy(menuGameOver.getPy() + menuGameOver.getAltura());

        menuGameOver.setAtivo(true);

    }

    @Override
    public void descarregar() {

    }

    @Override
    public void atualizar() {

    }

    @Override
    public void desenhar(Graphics2D g) {
        menuGameOver.desenha(g);
    }
}
