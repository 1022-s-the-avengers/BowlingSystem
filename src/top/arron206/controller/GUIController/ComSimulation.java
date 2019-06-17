package top.arron206.controller.GUIController;

import top.arron206.controller.ScoreSimulation.CompetitionSimulation;
import top.arron206.controller.ScoreSimulation.CompetitionType;
import top.arron206.model.Group;
import top.arron206.model.Member;

import java.util.ArrayList;
import java.util.List;

public class ComSimulation extends Thread {
    public static CompetitionSimulation competitionSimulation = new CompetitionSimulation();
    public static int type = 0;
    public static List<Member> singleR= new ArrayList<>();
    public static ArrayList <Group> doubleR= new ArrayList<>();
    public static ArrayList <Group> tripleR= new ArrayList<>();
    public static ArrayList<Group> pentaR= new ArrayList<>();
    public static ArrayList<Member> jyR= new ArrayList<>();
    @Override
    public void run(){
        System.out.println("123");
        switch(type){
            case 1 :
                    competitionSimulation.ordinaryCompetition(CompetitionType.Singles);
                    competitionSimulation.divideGroup(CompetitionType.Doubles);
                    Member.getRankList(singleR);
                    System.out.println(singleR);
                    break;
            case 2 :
                competitionSimulation.ordinaryCompetition(CompetitionType.Doubles);
                competitionSimulation.divideGroup(CompetitionType.Triples);
                int iii = Group.getRankList(doubleR ,"双人赛");
                break;
            case 3 :competitionSimulation.ordinaryCompetition(CompetitionType.Triples);
                    competitionSimulation.divideGroup(CompetitionType.Penta);
                    Group.getRankList(tripleR ,"三人赛");
                    break;
            case 4 :competitionSimulation.ordinaryCompetition(CompetitionType.Penta);
                    Group.getRankList(pentaR ,"五人赛");
                    break;
            case 5 :competitionSimulation.classicCompetition();
                    Member.getCreditList(jyR);
                    break;
        }

        System.out.println("234");
    }
}
