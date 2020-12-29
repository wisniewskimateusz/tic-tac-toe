package tictactoe;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Save implements Serializable {

    private final String scoreFileName = "score.txt";
    private final String currentScoreFileName = "currentScore.txt";
    String rankingFileName = "ranking.txt";
    List<Score> history = new ArrayList<>();
    List<String> result = new ArrayList<>();
    List<String> score = new ArrayList<>();

    public void saveScoreToFile(String scoreBuild) {
        Path path = Paths.get(scoreFileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            writer.write(scoreBuild);
            writer.newLine();
            System.out.println("Zapisano: " + scoreBuild);
        } catch (IOException e) {
            System.out.println("wystąpił błąd: " + e);
        }
    }

    public List<String> loadScoreFromFile() {
        Path file = Paths.get(scoreFileName);
        try (Stream<String> stream = Files.lines(file)) {
            result = stream
                    //.flatMap(string -> Stream.of(string.split("-")))
                    .collect(Collectors.toList());
            result.forEach(System.out::println);
            System.out.println("Wczytano.");
        } catch (IOException e) {
            System.out.println("wystąpił błąd: " + e);
        }
        return result;
    }

    public void saveCurrentProgressToFile(String scoreBuild) throws IOException {
        Path path = Paths.get(currentScoreFileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(scoreBuild);
            writer.newLine();
            System.out.println("Zapisano: " + scoreBuild);
        } catch (IOException e) {
            System.out.println("wystąpił błąd: " + e);
        }
    }

    public List<String> loadCurrentProgressFromFile() {
        Path file = Paths.get(currentScoreFileName);
        try (Stream<String> stream = Files.lines(file)) {
            score = stream
                    .flatMap(string -> Stream.of(string.split("-")))
                    .collect(Collectors.toList());
            score.forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("wystąpił błąd: " + e);
        }
        return score;
    }
}