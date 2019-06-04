package top.arron206.controller.ScoreSimulation;

import java.util.ArrayList;

public class test {
    public static void main(String...args) {
        ScoreGenerator scoreGenerator = ScoreGenerator.getInstance();
        scoreGenerator.generate(1);
        ArrayList<String> recordList = scoreGenerator.getBowlingRecord();
        for (String e : recordList)
            System.out.println(e);
        System.out.println(scoreGenerator.getTotalScore());
    }
}