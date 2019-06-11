package top.arron206.controller.ScoreSimulation;

import java.util.LinkedList;

public class DivideGroupSimulation {
    //分组数量groupAmount和每组成员数量memberAmount
    public static LinkedList<LinkedList<Integer>> getAllMembersList(int groupAmount, int memberAmount) {
        int temp, j;
        boolean[] isSelected = new boolean[groupAmount * memberAmount + 1];
        LinkedList<LinkedList<Integer>> allMembersList = new LinkedList<>();//队员成员信息
        for (int i = 1; i <= groupAmount; ++i) {
            j = 0;
            LinkedList<Integer> memberList = new LinkedList<>();
            do {
                temp = RandInteger.uniformRand(1, groupAmount * memberAmount);
                if (!isSelected[temp]) {
                    memberList.add(temp);
                    isSelected[temp] = true;
                    ++j;
                }
            } while (j < memberAmount);
            allMembersList.add(memberList);
        }
        return allMembersList;
    }

    //返回一个顺序序号列表
    public static LinkedList<Integer> getOrderIntegerList(int amount) {
        LinkedList<Integer> allGroupList = new LinkedList<>();
        for (int i = 1; i <= amount; ++i)
            allGroupList.add(i);
        return allGroupList;
    }
}
