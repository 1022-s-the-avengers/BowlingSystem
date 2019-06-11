package top.arron206.controller.ScoreSimulation;

import java.util.LinkedList;

public class DivideGroupSimulation {
    public static LinkedList<LinkedList<Integer>> getAllMemberList(LinkedList<Integer> allGroupList) {
        int temp, j;
        boolean[] flag = new boolean[61];
        LinkedList<LinkedList<Integer>> allMemberList = new LinkedList<>();//队员成员信息
        for (int i = 1; i <= allGroupList.size(); ++i) {
            j = 0;
            LinkedList<Integer> memberList = new LinkedList<>();
            do {
                temp = RandInteger.uniformRand(1, 60);
                if (!flag[temp]) {
                    memberList.add(temp);
                    flag[temp] = true;
                    ++j;
                }
            } while (j < (60/allGroupList.size()));
            allMemberList.add(memberList);
        }
        return allMemberList;
    }
    public static LinkedList<Integer> getAllGroupList(int amount) {
        LinkedList<Integer> allGroupList = new LinkedList<>();
        for (int i = 1; i <= amount; ++i)
            allGroupList.add(i);
        return allGroupList;
    }
    public static void main(String...args) {
//        var groupList = getAllGroupList(30);
//        var memberList = getAllMemberList(groupList);
//        for (var e : memberList)
//            ;
//            //System.out.println(e);

    }
}
