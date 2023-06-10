package jogo_pacman.base;

import javax.swing.*;
import java.awt.*;

public class MenuFinal extends Texto{

    private final ImageIcon imagemGameOver = new ImageIcon("C:\\Users\\victo\\CodigoFontes\\CodigosFontes\\Java\\pac_manOriginal\\src\\imagem\\telaGameOver.png");
    public void desenha(Graphics2D g) { //Mudar o menu com essa função aqui.
        g.drawImage(imagemGameOver.getImage(), 40, 10, 408, 232,  null);
    }
}
