package top.arron206.controller.ScoreSimulation;

import top.arron206.model.CompetitionInfo;
import top.arron206.model.Group;
import top.arron206.model.Member;
import java.util.*;

public class CompetitionSimulation {
    private static ArrayList<Member> memberList = new ArrayList<>();
    private static ArrayList<Member> classicList = new ArrayList<>();
    private static PerRoundSimulator perRoundSimulator = new PerRoundSimulator(60);
    private static LinkedList<Integer> descriptions = new LinkedList<>();
    private static LinkedList<Integer> fouls= new LinkedList<>();
    private static LinkedList<Integer> totalScore= new LinkedList<>();
    private static int[] resultArray = new int[3];
    public void start() {
        LinkedList<Integer> allGroupList;
        initializeMemberInfo();
        ordinaryCompetition("单人赛");
        Group.addGroup("双人赛");
        allGroupList = DivideGroupSimulation.getAllGroupList(30);
        Group.addGroupList(DivideGroupSimulation.getAllMemberList(allGroupList), allGroupList, "双人赛");
        memberList.clear();
        Member.getAllMembers(memberList);
        ordinaryCompetition("双人赛");
        Group.addGroup("三人赛");
        allGroupList = DivideGroupSimulation.getAllGroupList(20);
        Group.addGroupList(DivideGroupSimulation.getAllMemberList(allGroupList), allGroupList, "三人赛");
        memberList.clear();
        Member.getAllMembers(memberList);
        ordinaryCompetition("三人赛");
        Group.addGroup("五人赛");
        allGroupList = DivideGroupSimulation.getAllGroupList(12);
        Group.addGroupList(DivideGroupSimulation.getAllMemberList(allGroupList), allGroupList, "五人赛");
        memberList.clear();
        Member.getAllMembers(memberList);
        ordinaryCompetition("五人赛");
        Member.getRank(classicList);
        classicCompetition();
    }

    private void ordinaryCompetition(String competitionType) {
        LinkedList<Integer> allGroupScoreList = getAllGroupScoreList(competitionType);
        LinkedList<int []> perRoundScoreList = new LinkedList<>();
        for (Member e : memberList) {
            for (int i = 1; i <= 6; ++i) {
                perRoundSimulator.start(e.getId());//生成每局比赛信息
                resultArray = perRoundSimulator.getResultArray(i);
                //System.out.println(resultArray[2]);
                descriptions.add(resultArray[0]);
                fouls.add(resultArray[1]);
                totalScore.add(resultArray[2]);
                if (!competitionType.equals("单人赛"))
                    allGroupScoreList.set(getGroupId(competitionType, e) - 1, (allGroupScoreList.get(getGroupId(competitionType, e) - 1) + resultArray[2]));//团队总分累加
                perRoundScoreList.add(perRoundSimulator.getResArray(e.getId(), i));
            }
        }
        int iii = Member.addAllRes(perRoundScoreList, competitionType);
        System.out.println(iii);
        Member.updateAllScore(DivideGroupSimulation.getAllGroupList(60),totalScore);
        CompetitionInfo.insertList(competitionType, descriptions, fouls);
        if (!competitionType.equals("单人赛")) {
            Group.updateTotalScoreList(allGroupScoreList, getAllGroupScoreList(competitionType));
        }
    }
    private LinkedList<Integer> getAllGroupScoreList(String competitionType) {
        switch (competitionType) {
            case "双人赛" :
                return DivideGroupSimulation.getAllGroupList(30);
            case "三人赛" :
                return DivideGroupSimulation.getAllGroupList(20);
            case "五人赛" :
                return DivideGroupSimulation.getAllGroupList(12);
        }
        return null;
    }

    private int getGroupId(String competitionType, Member member) {
        switch (competitionType) {
            case "双人赛" :
                return member.getTeamIdDouble();
            case "三人赛" :
                return member.getTeamIdTriple();
            case "五人赛" :
                return member.getTeamIdPenta();
        }
        return 0;
    }

    private void classicCompetition() {
        int credits[] = new int[16];
        int totalScore1, totalScore2;
        for (int num1 = 0; num1 < 16; ++num1) {
            for (int num2 = num1; num2 < 16; ++num2) {
                if (num1 == num2)
                    continue;
                perRoundSimulator.start(classicList.get(num1).getId());
                totalScore1 = perRoundSimulator.getResultArray(1)[2];
                perRoundSimulator.start(classicList.get(num2).getId());
                totalScore2 = perRoundSimulator.getResultArray(1)[2];
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

    private void insertCredit (int[] credits) {
        Member member;
        for (int i = 0; i < 15; ++i) {
            member = memberList.get(memberList.indexOf(new Member(classicList.get(i).getId())));
            member.setCredit(credits[i]);
            member.insertMember();
        }
    }
    private void initializeMemberInfo() {
        Member.insertAllMember(BasicInfoGenerator.getNameList(60), BasicInfoGenerator.getProvinceList(60));
        Member.getAllMembers(memberList);
    }

    public static void main(String... args) {
        CompetitionSimulation competitionSimulation = new CompetitionSimulation();
        competitionSimulation.start();
    }
}
