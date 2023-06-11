package jogo_pacman;

import jogo_pacman.base.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Ranking {
    private static final String RANKING_FILE = "testeRanking.txt";

    private ArrayList<Player> rank;

    public Ranking() {
        rank = new ArrayList<>();
    }

    public void loadRanking() {
        //Copiar aqui o Absolet PATH
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\victo\\CodigoFontes\\CodigosFontes\\Java\\pac_manOriginal\\src\\jogo_pacman\\base\\testeRanking"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    Player player = new Player(name, score);
                    rank.add(player);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de ranking: " + e.getMessage());
        }
    }

    public void updateRanking(String playerName, int playerScore) {
        Player player = new Player(playerName, playerScore);
        rank.add(player);
        Collections.sort(rank);
    }

    public void saveRanking() {
        //Copiar aqui o Absolet PATH
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\victo\\CodigoFontes\\CodigosFontes\\Java\\pac_manOriginal\\src\\jogo_pacman\\base\\testeRanking"))) {
            for (Player player : rank) {
                String line = player.getName() + "," + player.getScore();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao gravar o arquivo de ranking: " + e.getMessage());
        }
    }

    public ArrayList<Player> getRank() {
        return rank;
    }

    public void setRank(ArrayList<Player> rank) {
        this.rank = rank;
    }
}
