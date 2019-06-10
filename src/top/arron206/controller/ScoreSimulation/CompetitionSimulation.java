package top.arron206.controller.ScoreSimulation;

import top.arron206.model.Member;
import java.util.*;

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
                Member.CompetitionRes res = e.new CompetitionRes(perRoundSimulator.getEachTurn(), competitionType, i);
                e.insertMember();
                res.insertRes();
            }
        }
    }

    private void classicCompetition(int [] classicPlayers) {
        int credits[] = new int[16];
        int totalScore1, totalScore2;
        for (int num1 = 0; num1 < 16; ++num1) {
            for (int num2 = 0; num2 < 16; ++num2) {
                if (num1 == num2)
                    continue;
                perRoundSimulator.generate("精英赛", classicPlayers[num1], 1);
                totalScore1 = perRoundSimulator.getTotalScore();
                perRoundSimulator.generate("精英赛", classicPlayers[num2], 1);
                totalScore2 = perRoundSimulator.getTotalScore();
                if (totalScore1 > totalScore2)
                    credits[num1]++;
                else
                    credits[num2]++;
            }
        }
        insertCredit(classicPlayers, credits);
    }

    private void insertCredit (int [] classicPlayers, int[] credits) {
        Member member;
        for (int i = 0; i < 15; ++i) {
            member = memberList.get(memberList.indexOf(new Member(classicPlayers[i])));
            member.setCredit(credits[i]);
            member.insertMember();
        }
    }
    private void initializeMemberInfo() {
        for (int i = 1; i <= 60; ++i) {
            BasicInfoGenerator basicInfoGenerator = new BasicInfoGenerator();
            Member member = new Member(basicInfoGenerator.getRandomName(), basicInfoGenerator.getRandomProvince());
            memberList.add(member);
            member.insertMember();
        }
        memberList.clear();
        Member.getAllMembers(memberList);
    }

    public static void main(String... args) {
        CompetitionSimulation competitionSimulation = new CompetitionSimulation();
        competitionSimulation.start();
    }
}
