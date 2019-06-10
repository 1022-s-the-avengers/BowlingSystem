package top.arron206.controller.ScoreSimulation;

import top.arron206.model.Group;

import java.util.ArrayList;

public class DivideGroupSimulation {
    public static void divideGroup(String competitionType) {
        int temp, j, groupMemberAmount = getMemberAmount(competitionType);
        boolean[] flag = new boolean[61];
        ArrayList<Integer> memberNumbers = new ArrayList<>();//队员成员信息
        Group group;
        Group.addGroup(competitionType);
        for (int i = 1; i <= (60 / groupMemberAmount); ++i) {
            j = 0;
            do {
                temp = RandIntegerGenerator.uniformRand(1, 60);
                if (!flag[temp]) {
                    memberNumbers.add(temp);
                    ++j;
                }
            }while (j < groupMemberAmount);
            group = new Group(i, competitionType);
            group.addGroupMember(memberNumbers);
            memberNumbers.clear();
        }
    }
    private static int getMemberAmount(String competitionType) {
        switch (competitionType) {
            case "双人赛" :
                return 2;
            case "三人赛" :
                return 3;
            case "五人赛" :
                return 5;
        }
        return 0;
    }
}
