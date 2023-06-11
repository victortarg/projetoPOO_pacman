package jogo_pacman.base;

public class Player implements Comparable<Player> {
    private String name;
    private int score;

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int compareTo(Player other) {
        // Ordenar em ordem decrescente de pontuação
        return Integer.compare(other.score, this.score);
    }
}
