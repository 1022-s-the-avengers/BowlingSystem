package top.arron206.controller.ScoreSimulation;

import top.arron206.model.Member;

import java.util.ArrayList;

public class CompetitionSimulation {
    private static ArrayList<Member> memberList = new ArrayList<>();
    private static PerRoundSimulator perRoundSimulator = PerRoundSimulator.getInstance();
    public void start() {
        initializeMemberInfo();
        ordinaryCompetition("单人赛");
        ordinaryCompetition("双人赛");
        ordinaryCompetition("三人赛");
        ordinaryCompetition("五人赛");
    }
    private void ordinaryCompetition(String competitionType) {
        for (Member e : memberList) {
            for (int i = 1; i <= 6; ++i) {
                perRoundSimulator.generate(competitionType, e.getId(), i);//生成每局比赛信息
                perRoundSimulator.getEachTurn();
            }
        }
    }
    private void initializeMemberInfo() {
        BasicInfoGenerator basicInfoGenerator = new BasicInfoGenerator();
        Member member = new Member(basicInfoGenerator.getRandomName(), basicInfoGenerator.getRandomProvince());
    }
    public static void main(String...args) {
        CompetitionSimulation competitionSimulation = new CompetitionSimulation();
        competitionSimulation.start();
    }
}
