package jogo_pacman.base;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RankingPanel extends JPanel {
    private ArrayList<Player> ranking;

    public RankingPanel(ArrayList<Player> ranking) {
        this.ranking = ranking;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLUE);
        g.setFont(new Font("Consolas", Font.BOLD, 16));

        int y = 20;
        g.drawString("---------- Ranking ----------", 40, y);

        for (Player player : ranking) {
            y += 20;
            g.drawString(player.getName() + " - " + player.getScore(), 40, y);
        }

        y += 20;
        g.drawString("-----------------------------", 40, y);
    }
}
