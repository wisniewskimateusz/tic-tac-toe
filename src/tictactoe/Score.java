package tictactoe;

import java.io.Serializable;

public class Score implements Serializable {

    private int playedGames;
    private int wonGames;
    private int loosesGames;
    private int draw;

    public Score() {
    }

    public Score(int wonGames, int loosesGames) {
        this.wonGames = wonGames;
        this.loosesGames = loosesGames;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(int playedGames) {
        this.playedGames = playedGames;
    }

    public int getWonGames() {
        return wonGames;
    }

    public void setWonGames(int wonGames) {
        this.wonGames = wonGames;
    }

    public int getLoosesGames() {
        return loosesGames;
    }

    public void setLoosesGames(int loosesGames) {
        this.loosesGames = loosesGames;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    @Override
    public String toString() {
        //return "" + playedGames + " " + wonGames + " " + loosesGames + " " + draw;
        return "Gracz: " + wonGames + " - " + loosesGames + " :Komputer ";
    }
}
