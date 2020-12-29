package tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicTacToeController {
    @FXML
    private Button newGameButton;
    @FXML
    private Button resetButton;
    @FXML
    private ToggleButton levelOneButton;
    @FXML
    private ToggleButton levelTwoButton;

    private ToggleGroup toggleGroup;

    @FXML
    private Button showRankingButton;

    @FXML
    private Label resultLabel;
    @FXML
    private Label scoreLabel;

    @FXML
    private GameButton button1;
    @FXML
    private GameButton button2;
    @FXML
    private GameButton button3;
    @FXML
    private GameButton button4;
    @FXML
    private GameButton button5;
    @FXML
    private GameButton button6;
    @FXML
    private GameButton button7;
    @FXML
    private GameButton button8;
    @FXML
    private GameButton button9;

    @FXML
    private Button saveScoreButton;
    @FXML
    private Button loadScoreButton;
    @FXML
    private Button saveProgressButton;
    @FXML
    private Button loadProgressButton;

    private GameButton[] buttons = new GameButton[9];
    private Random generator = new Random();
    private int playedGames = 0;
    private int wonGames = 0;
    private int loosesGames = 0;
    private int draw = 0;

    private int score1 = 0;
    private int score2 = 0;
    int tie = 0;

    private String p1 = "Gracz";
    private String p2 = "Komputer";

    Save save = new Save();
    Alert alert = null;
    Score score = new Score();

    List<String> rankingHistory = save.loadScoreFromFile();
    List<String> currentProgress = new ArrayList<>();

    @FXML
    void newGameButtonOnAction() {
        newGameButton.setOnAction(event -> {
            playedGames += 1;
            //resultLabel.setText("");
            for (int i = 0; i < 9; i++) {
                buttons[i].setState(0);
                //setState(buttons[i], 0);
                buttons[i].setDisable(false);
            }
            tie = 0;
        });
    }

    @FXML
    void resetButtonOnAction() {
        resetButton.setOnAction(event -> {
            scoreLabel.setText("");
            for (int i = 0; i < 9; i++) {
                buttons[i].setState(0);
                buttons[i].setDisable(false);
            }
            score1 = 0;
            score2 = 0;
            tie = 0;
            scoreLabel.setText(score1 + " - " + score2);
        });
    }

    @FXML
    void boardButtonOnAction(ActionEvent event) {
        initButtons();
        for (int i = 0; i < 9; i++) {
            if (buttons[i] == event.getSource()) {
                buttons[i].setState(1);
                tie++;
                //setLevel();
                levelOneButtonOnAction();
                //levelTwo();
            }
            score = new Score(wonGames, loosesGames);
        }
    }

    @FXML
    void saveScoreButtonOnAction() {
        saveScoreButton.setOnAction(event -> saveScore());
    }

    private void saveScore() {
        StringBuilder scoreBuild = new StringBuilder();
        scoreBuild.append(wonGames);
        scoreBuild.append("-");
        scoreBuild.append(loosesGames);
        scoreBuild.toString();
        save.saveScoreToFile(scoreBuild.toString());
    }

    @FXML
    void loadScoreButtonOnAction() {
        loadScoreButton.setOnAction(event -> {
            rankingHistory = save.loadScoreFromFile();
            System.out.println(rankingHistory.size());
        });
    }

    @FXML
    void saveProgressButtonOnAction() {
        saveProgressButton.setOnAction(event -> saveCurrentProgress());
    }

    private void saveCurrentProgress() {
        StringBuilder scoreBuild = new StringBuilder();
        scoreBuild.append(score1);
        scoreBuild.append("-");
        scoreBuild.append(score2);
        scoreBuild.toString();
        try {
            save.saveCurrentProgressToFile(scoreBuild.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loadProgressButtonOnAction() {
        loadProgressButton.setOnAction(event -> loadCurrentProgress());
    }

    private void loadCurrentProgress() {
        currentProgress = save.loadCurrentProgressFromFile();
        score1 = Integer.parseInt(currentProgress.get(0));
        score2 = Integer.parseInt(currentProgress.get(1));
        scoreLabel.setText(score1 + " - " + score2);
    }

    @FXML
    void showRankingButtonOnAction() {
        showRankingButton.setOnAction(event -> showRanking());
    }

    private void showRanking() {
        StringBuilder rankingBuilder = new StringBuilder();
        rankingHistory = save.loadScoreFromFile();
        for (int i = 0; i < rankingHistory.size(); i++) {
            rankingBuilder.append("Gracz ");
            rankingBuilder.append(rankingHistory.get(i));
            rankingBuilder.append(" Komputer");
            rankingBuilder.append("\n");
        }
        alert = new Alert(Alert.AlertType.INFORMATION, rankingBuilder.toString());
        alert.setTitle("Ranking");
        alert.setHeaderText("Gracz vs Komputer");
        alert.show();
    }

    private void setLevel() {
        levelOneButton.setToggleGroup(toggleGroup);
        levelTwoButton.setToggleGroup(toggleGroup);

        if (levelOneButton.isSelected()) {
            levelTwoButton.setSelected(false);
            levelOneButtonOnAction();
        }

        if (levelTwoButton.isSelected()) {
            levelOneButton.setSelected(false);
        }
    }

    @FXML
    void levelOneButtonOnAction() {
        System.out.println("Level 1");
        for (int x = 0; x < 100; x++) {
            int random = generator.nextInt(9);
            if (buttons[random].getState() == 0) {
                buttons[random].setState(-1);
                checkScore();
                break;
            }
        }
    }

    @FXML
    void levelTwoButtonOnAction() {
        System.out.println("Level 2");
        //row 1
        if (buttons[0].getState() + buttons[1].getState() == -2) {
            buttons[3].setState(-1);
        }
        if (buttons[0].getState() + buttons[2].getState() == -2) {
            buttons[1].setState(-1);
        }

        //row2
        if (buttons[3].getState() + buttons[4].getState() == -2) {
            buttons[5].setState(-1);
        }
        if (buttons[3].getState() + buttons[5].getState() == -2) {
            buttons[4].setState(-1);
        }

        //row3
        if (buttons[6].getState() + buttons[7].getState() == -2) {
            buttons[8].setState(-1);
        }
        if (buttons[6].getState() + buttons[8].getState() == -2) {
            buttons[7].setState(-1);
        }

        //dialeg
        if (buttons[0].getState() + buttons[4].getState() == -2) {
            buttons[8].setState(-1);
        }
        if (buttons[0].getState() + buttons[8].getState() == -2) {
            buttons[4].setState(-1);
        }
        if (buttons[2].getState() + buttons[4].getState() == -2) {
            buttons[6].setState(-1);
        }
        if (buttons[2].getState() + buttons[6].getState() == -2) {
            buttons[4].setState(-1);
        } else {
            for (GameButton button : buttons) {
                if (button.getState() == 0) {
                    button.setState(-1);
                    tie++;
                    break;
                }
            }
        }
        checkScore();
    }

    public void initButtons() {
        buttons[0] = button1;
        buttons[1] = button2;
        buttons[2] = button3;
        buttons[3] = button4;
        buttons[4] = button5;
        buttons[5] = button6;
        buttons[6] = button7;
        buttons[7] = button8;
        buttons[8] = button9;
    }

    public void scoreLabel() {
        scoreLabel.setText(score1 + " - " + score2);
    }

    private void checkScore() {
        //Row 1
        if (buttons[0].getState() + buttons[1].getState() + buttons[2].getState() == 3) {
            resultLabel.setText(p1 + " wygrał!");
            for (int i = 0; i < 9; i++) {
                buttons[i].setDisable(true);
            }
            score1++;
            playedGames++;
            wonGames++;
            scoreLabel();
            //scoreLabel.setText(score1 + " - " + score2);
        }
        if (buttons[0].getState() + buttons[1].getState() + buttons[2].getState() == -3) {
            resultLabel.setText(p2 + " wygrał!");
            for (int i = 0; i < 9; i++) {
                buttons[i].setDisable(true);
            }
            score2++;
            playedGames++;
            loosesGames++;
            scoreLabel();
            //scoreLabel.setText(score1 + " - " + score2);
        }
        //Row 2
        if (buttons[3].getState() + buttons[4].getState() + buttons[5].getState() == 3) {
            resultLabel.setText(p1 + " wygrał!");
            for (int i = 0; i < 9; i++) {
                buttons[i].setDisable(true);
            }
            score1++;
            playedGames++;
            wonGames++;
            scoreLabel();
            //scoreLabel.setText(score1 + " - " + score2);
        }
        if (buttons[3].getState() + buttons[4].getState() + buttons[5].getState() == -3) {
            resultLabel.setText(p2 + " wygrał!");
            for (int i = 0; i < 9; i++) {
                buttons[i].setDisable(true);
            }
            score2++;
            playedGames++;
            loosesGames++;
            scoreLabel();
            //scoreLabel.setText(score1 + " - " + score2);
        }
        //Row 3
        if (buttons[6].getState() + buttons[7].getState() + buttons[8].getState() == 3) {
            resultLabel.setText(p1 + " wygrał!");
            for (int i = 0; i < 9; i++) {
                buttons[i].setDisable(true);
            }
            score1++;
            playedGames++;
            wonGames++;
            scoreLabel();
            //scoreLabel.setText(score1 + " - " + score2);
        }
        if (buttons[6].getState() + buttons[7].getState() + buttons[8].getState() == -3) {
            resultLabel.setText(p2 + " wygrał!");
            for (int i = 0; i < 9; i++) {
                buttons[i].setDisable(true);
            }
            score2++;
            playedGames++;
            loosesGames++;
            scoreLabel();
            //scoreLabel.setText(score1 + " - " + score2);
        }
        //Column 1
        if (buttons[0].getState() + buttons[3].getState() + buttons[6].getState() == 3) {
            resultLabel.setText(p1 + " wygrał!");
            for (int i = 0; i < 9; i++) {
                buttons[i].setDisable(true);
            }
            score1++;
            playedGames++;
            wonGames++;
            scoreLabel();
            //scoreLabel.setText(score1 + " - " + score2);
        }
        if (buttons[0].getState() + buttons[3].getState() + buttons[6].getState() == -3) {
            resultLabel.setText(p2 + " wygrał!");
            for (int i = 0; i < 9; i++) {
                buttons[i].setDisable(true);
            }
            score2++;
            playedGames++;
            loosesGames++;
            scoreLabel();
            //scoreLabel.setText(score1 + " - " + score2);
        }
        //Column 2
        if (buttons[1].getState() + buttons[4].getState() + buttons[7].getState() == 3) {
            resultLabel.setText(p1 + " wygrał!");
            for (int i = 0; i < 9; i++) {
                buttons[i].setDisable(true);
            }
            score1++;
            playedGames++;
            wonGames++;
            scoreLabel();
            //scoreLabel.setText(score1 + " - " + score2);
        }
        if (buttons[1].getState() + buttons[4].getState() + buttons[7].getState() == -3) {
            resultLabel.setText(p2 + " wygrał!");
            for (int i = 0; i < 9; i++) {
                buttons[i].setDisable(true);
            }
            score2++;
            playedGames++;
            loosesGames++;
            scoreLabel();
            //scoreLabel.setText(score1 + " - " + score2);
        }
        //Column 3
        if (buttons[2].getState() + buttons[5].getState() + buttons[8].getState() == 3) {
            resultLabel.setText(p1 + " wygrał!");
            for (int i = 0; i < 9; i++) {
                buttons[i].setDisable(true);
            }
            score1++;
            playedGames++;
            wonGames++;
            scoreLabel();
            //scoreLabel.setText(score1 + " - " + score2);
        }
        if (buttons[2].getState() + buttons[5].getState() + buttons[8].getState() == -3) {
            resultLabel.setText(p2 + " wygrał!");
            for (int i = 0; i < 9; i++) {
                buttons[i].setDisable(true);
            }
            score2++;
            playedGames++;
            loosesGames++;
            scoreLabel();
            //scoreLabel.setText(score1 + " - " + score2);
        }
        //Diagonal 1:
        if (buttons[0].getState() + buttons[4].getState() + buttons[8].getState() == 3) {
            resultLabel.setText(p1 + " wygrał!");
            for (int i = 0; i < 9; i++) {
                buttons[i].setDisable(true);
            }
            score1++;
            playedGames++;
            wonGames++;
            scoreLabel();
            //scoreLabel.setText(score1 + " - " + score2);
        }
        if (buttons[0].getState() + buttons[4].getState() + buttons[8].getState() == -3) {
            resultLabel.setText(p2 + " wygrał!");
            for (int i = 0; i < 9; i++) {
                buttons[i].setDisable(true);
            }
            score2++;
            playedGames++;
            loosesGames++;
            scoreLabel();
            //scoreLabel.setText(score1 + " - " + score2);
        }
        //Diagonal 2:
        if (buttons[2].getState() + buttons[4].getState() + buttons[6].getState() == 3) {
            resultLabel.setText(p1 + " wygrał!");
            for (int i = 0; i < 9; i++) {
                buttons[i].setDisable(true);
            }
            score1++;
            playedGames++;
            wonGames++;
            scoreLabel();
            //scoreLabel.setText(score1 + " - " + score2);
        }
        if (buttons[2].getState() + buttons[4].getState() + buttons[6].getState() == -3) {
            resultLabel.setText(p2 + " wygrał");
            for (int i = 0; i < 9; i++) {
                buttons[i].setDisable(true);
            }
            score2++;
            playedGames++;
            loosesGames++;
            scoreLabel();
            //scoreLabel.setText(score1 + " - " + score2);
        }
        //Tie:
        if (tie == 9) {
            resultLabel.setText("Remis!");
        }
    }
}