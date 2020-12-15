package com.example.quizapp1;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
//Dao データアクセスオブジェクトの略.各種データにアクセスする役割のクラス
public class QuizFileDao {

    private static final String FILE_PATH = "quizzes.txt";

    public void write(List<Quiz> quizzes) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Quiz quiz: quizzes){
            lines.add(quiz.toString());
        }
        //第一引数書き込み先のファイルパス（場所）
        //第二引数、、書き込むデータlist<string>
        Path path = Paths.get(FILE_PATH);
        Files.write(path,lines);
    }
    public List<Quiz> read() throws IOException {
        Path path = Paths.get(FILE_PATH);
        List<String> lines = Files.readAllLines(path);

        List<Quiz> quizzes = new ArrayList<>();
        for (String line: lines){
            quizzes.add(Quiz.fromString(line));
        }
        return quizzes;
    }
}
