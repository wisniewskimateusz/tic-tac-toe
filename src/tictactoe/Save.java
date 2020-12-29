package tictactoe;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Save implements Serializable {

    private final String scoreFileName = "score.txt";
    private final String currentScoreFileName = "currentScore.txt";
    private final String boardMapFileName = "boardMap.txt";

    List<String> result = new ArrayList<>();
    List<String> score = new ArrayList<>();
    Map<Integer, Integer> boardMap = new HashMap<>();

    public void saveBoardMap(Map<Integer, Integer> boardMap) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(boardMapFileName));
            oos.writeObject(boardMap);
            oos.close();
            System.out.println("Zapisano mapę.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public Map<Integer, Integer> loadBoardMap() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(boardMapFileName));
            Object readMap = ois.readObject();
            if(readMap instanceof HashMap) {
                boardMap.putAll((HashMap) readMap);
                System.out.println("Wczytano mapę.");
            }
            ois.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return boardMap;
    }

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