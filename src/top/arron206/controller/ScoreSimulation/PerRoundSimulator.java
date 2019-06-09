package top.arron206.controller.ScoreSimulation;

import top.arron206.model.CompetitionInfo;

public class PerRoundSimulator {
    //类中对象的唯一实例
    private static PerRoundSimulator instance = null;
    //每次投球倒瓶数
    private static int eachTime[] = new int[21];
    //每次投球情况
    private static Condition conditions[] = new Condition[21];
    //每轮投球得分
    private static int eachTurn[] = new int[10];
    //当前选手编号
    private static int number;
    //本次比赛选手实力情况
    private static int playerLevel[] = new int[60];

    private PerRoundSimulator() {
        resetGenerator();
    }

    //重新开始比赛，随机生成选手的实力情况
    private static void resetGenerator() {
        for (int i = 0; i < 60; ++i)
            playerLevel[i] = RandIntegerGenerator.uniformRand(1, 11);
    }

    //单例模式
    public static PerRoundSimulator getInstance() {
        if (instance == null)
            instance = new PerRoundSimulator();
        return instance;
    }

    //产生各种比赛信息
    public void generate(int number) throws IllegalArgumentException {
        if (number < 60)//编号不可大于60
            this.number = number;
        else
            throw new IllegalArgumentException("编号大于60");
        for (int i = 0; i < 21; ++i) {
            eachTime[i] = 0;
            conditions[i] = Condition.NULL;
        }
        bowlingBall();
        calculateScore();
    }

    //模拟投球，并记录情况
    private static void bowlingBall() {
        int first, second;
        for (int i = 0; i < 10; ++i) {
            first = RandScore();//第一次投球
            if (first == 10) {//全中
                eachTime[2 * i] = 10;
                conditions[2 * i] = Condition.Strike;
                if (i != 9) {//第十轮外全中不进行第二局
                    conditions[2 * i + 1] = Condition.NULL;
                    continue;
                }
            } else //未全中或犯规
                recordMissAndFoul(first, 2 * i);
            second = RandScore();//第二次投球
            if (second == 10) {//补中
                eachTime[2 * i + 1] = 10;
                conditions[2 * i + 1] = Condition.Spare;
            } else //未补中或犯规
                recordMissAndFoul(second, 2 * i + 1);
            if (conditions[18] == Condition.Strike || conditions[19] == Condition.Spare)//如果第十局全中或补中要开启第三局
                recordMissAndFoul(RandScore(), 20);
        }
    }

    private static int RandScore() {
        return RandIntegerGenerator.normalRand(-1, 10, playerLevel[number], 1);
    }

    //处理部分命中或犯规。amount为倒瓶数，time为投球次序数
    private static void recordMissAndFoul(int amount, int time) {
        if (amount < 0) //记录犯规，倒瓶数视为0
            conditions[time] = Condition.Foul;
        else {
            eachTime[time] = amount;//倒瓶数
            conditions[time] = Condition.Miss;
        }
    }

    //计算每轮得分
    private static void calculateScore() {
        for (int i = 0; i < 10; ++i) {
            switch (conditions[2 * i]) {//第一次投球
                case Strike://全中
                    calculateStrikeAndSpare(i, 2 * i, 2);
                    break;
                default: {//第二次投球
                    switch (conditions[2 * i + 1]) {
                        case Spare://补中
                            calculateStrikeAndSpare(i, 2 * i + 1, 1);
                            break;
                        default://部分命中或犯规
                            eachTurn[i] = eachTime[2 * i] + eachTime[2 * i + 1];
                    }
                }
            }
        }
    }

    //计算全中或补中后两次或一次有效投球的倒瓶数之和（即全中和补中的分数）
    private static void calculateStrikeAndSpare(int round, int time, int count) {
        eachTurn[round] = 10;
        for (int i = 0, j = 1; i < count; ++j) {
            if (conditions[time + j] != Condition.NULL) {
                eachTurn[round] += eachTime[time + j];
                ++i;
            }
        }
    }

    public void writeCompetitionInfo(String competitionType, int round) {
        CompetitionInfo competitionInfo = new CompetitionInfo();
        StringBuffer description = new StringBuffer();
        for (int i = 0; i < 21; ++i) {
            if (conditions[i] != Condition.NULL) {
                competitionInfo.setMemberId(number);//选手号码
                competitionInfo.setCompetitionType(competitionType);//比赛类型
                competitionInfo.setRound(round);//比赛局数
                competitionInfo.setTurn(i / 2 + 1);//比赛轮数
                if (i != 20)//比赛次数
                    competitionInfo.setTimes(i % 2 + 1);
                else
                    competitionInfo.setTimes(3);
                description.append(conditions[i]);
                if (conditions[i] == Condition.Miss) //部分命中要说明命中数量
                    description.append(eachTime[i]);
                competitionInfo.setDescription(description.toString());
                System.out.println(competitionInfo);
                //competitionInfo.insertInfo();
                description.setLength(0);//清空字符串
            }
        }
    }

    public int[] getPlayerLevel() {
        return playerLevel;
    }
}