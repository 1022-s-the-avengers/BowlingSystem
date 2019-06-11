package top.arron206.controller.ScoreSimulation;

import top.arron206.model.CompetitionInfo;
import top.arron206.model.Group;
import top.arron206.model.Member;

import java.util.*;

public class CompetitionSimulation {
    private int playerAmount = 60;
    private ArrayList<Member> memberList = new ArrayList<>();
    private PerRoundSimulator perRoundSimulator = new PerRoundSimulator();
    private LinkedList<Integer> descriptions = new LinkedList<>();
    private LinkedList<Integer> fouls = new LinkedList<>();
    private LinkedList<Integer> totalScore = new LinkedList<>();
    private int[] resultArray = new int[3];
    //本次比赛选手实力情况
    private ArrayList<Integer> playersLevel = new ArrayList<>();

    public CompetitionSimulation() {
        initializeMemberInfo();
        setPlayLevel();
    }

    public CompetitionSimulation(int playerAmount) {
        setPlayerAmount(playerAmount);
        setPlayLevel();
        initializeMemberInfo();
    }

    private void setPlayLevel() {
        for (int i = 0; i < playerAmount; ++i)
            playersLevel.add(RandInteger.uniformRand(1, 11));
    }

    public void ordinaryCompetition(CompetitionType competitionType) {
        if (competitionType != CompetitionType.Singles) {
            LinkedList<Integer> allGroupsList;
            Group.addGroup(competitionType.toString());//创建组别
            allGroupsList = DivideGroupSimulation.getOrderIntegerList(playerAmount / competitionType.getAmount());
            Group.addGroupList(DivideGroupSimulation.getAllMembersList(playerAmount / competitionType.getAmount(),
                    competitionType.getAmount()), allGroupsList, CompetitionType.Singles.toString());
            memberList.clear();
            Member.getAllMembers(memberList);//更新成员信息
        }
        ordinaryCompetition(competitionType);
    }

    public void classicCompetition() {
        ArrayList<Member> classicList = new ArrayList<>();
        Member.getRank(classicList);
        int credits[] = new int[16];
        int totalScore1, totalScore2;
        for (int num1 = 0; num1 < 16; ++num1) {
            for (int num2 = num1; num2 < 16; ++num2) {
                if (num1 == num2)
                    continue;
                perRoundSimulator.start(classicList.get(num1).getId(), getPlayLevel(classicList.get(num1)), 1);
                totalScore1 = perRoundSimulator.getResultArray()[2];
                perRoundSimulator.start(classicList.get(num2).getId(), getPlayLevel(classicList.get(num2)), 1);
                totalScore2 = perRoundSimulator.getResultArray()[2];
                if (totalScore1 > totalScore2)
                    credits[num1]++;
                else
                    credits[num2]++;
            }
        }
        LinkedList<Integer> classicNumberList = new LinkedList<>();
        for (Member e : classicList)
            classicNumberList.add(e.getId());
        LinkedList<Integer> avc = new LinkedList<>();
        for (int e : credits)
            avc.add(e);
        Member.insertAllCredit(classicNumberList, avc);
    }

    private void ordinaryCompetitionSimulation(CompetitionType competitionType) {
        LinkedList<Integer> allGroupScoreList = DivideGroupSimulation.getOrderIntegerList(playerAmount / competitionType.getAmount());
        LinkedList<int[]> perRoundScoreList = new LinkedList<>();
        for (Member e : memberList) {
            for (int i = 1; i <= 6; ++i) {
                perRoundSimulator.start(e.getId(), playersLevel.get(e.getId()), i);//生成每局比赛信息
                resultArray = perRoundSimulator.getResultArray();
                descriptions.add(resultArray[0]);
                fouls.add(resultArray[1]);
                totalScore.add(resultArray[2]);
                if (competitionType != CompetitionType.Singles)
                    allGroupScoreList.set(getGroupId(competitionType, e) - 1, (allGroupScoreList.get(getGroupId(competitionType, e) - 1) + resultArray[2]));//团队总分累加
                perRoundScoreList.add(perRoundSimulator.getResArray());
            }
        }
        int iii = Member.addAllRes(perRoundScoreList, competitionType.toString());
        System.out.println(iii);
        Member.updateAllScore(DivideGroupSimulation.getOrderIntegerList(playerAmount), totalScore);
        CompetitionInfo.insertList(competitionType.toString(), descriptions, fouls);
        if (competitionType != CompetitionType.Singles)
            Group.updateTotalScoreList(allGroupScoreList, DivideGroupSimulation.getOrderIntegerList(playerAmount / competitionType.getAmount()));
    }

    private int getGroupId(CompetitionType competitionType, Member member) {
        switch (competitionType) {
            case Doubles:
                return member.getTeamIdDouble();
            case Triples:
                return member.getTeamIdTriple();
            case Penta:
                return member.getTeamIdPenta();
        }
        return 0;
    }

    private int getPlayLevel(Member member) {
        return playersLevel.get(member.getId());
    }
    private void initializeMemberInfo() {
        Member.insertAllMember(BasicInfoGenerator.getNameList(playerAmount), BasicInfoGenerator.getProvinceList(playerAmount));
        Member.getAllMembers(memberList);//更新成员信息
    }

    public ArrayList<Member> getMemberList() {
        return memberList;
    }

    public void setPlayerAmount(int playerAmount) {
        this.playerAmount = playerAmount;
    }
}
