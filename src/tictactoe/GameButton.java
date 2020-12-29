package tictactoe;

import javafx.scene.control.Button;

import java.io.Serializable;

public class GameButton extends Button implements Serializable {

    public GameButton() {
        super();
    }

    private int state;

    public void setState(int x) {
        if (x == 0) {
            this.setText("");
            this.state = 0;
        } else if (x == 1) {
            this.setText("X");
            this.state = 1;
            this.setDisable(true);
        } else if (x == -1) {
            this.setText("O");
            this.state = -1;
            this.setDisable(true);
        }
    }

    public int getState() {
        return state;
    }

    @Override
    public String toString() {
        return "Status: " + state;
    }
}