package top.arron206.controller.ScoreSimulation;

public class test {
    public static void main(String...args) {
        var perRoundSimulator = PerRoundSimulator.getInstance();
        for (int i = 1; i <= 20; ++i) {
            System.out.println(perRoundSimulator.getPlayerLevel()[i]);
            perRoundSimulator.generate(i);
            perRoundSimulator.writeCompetitionInfo("精英赛", 4);
        }
    }
}