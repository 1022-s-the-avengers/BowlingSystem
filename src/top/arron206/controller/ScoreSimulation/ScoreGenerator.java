package top.arron206.controller.ScoreSimulation;

import java.util.ArrayList;

public class ScoreGenerator {
    //类中对象的唯一实例
    private static ScoreGenerator instance = null;
    //每次投球倒瓶数
    private static int eachTime[] = new int[21];
    //每次投球情况
    private static Condition conditions[] = new Condition[21];
    //每轮投球得分
    private static int eachRound[] = new int[10];
    //选手编号
    private int number;

    private ScoreGenerator() {
    }

    //单例模式
    public static ScoreGenerator getInstance() {
        if (instance == null)
            instance = new ScoreGenerator();
        return instance;
    }

    //产生各种比赛信息
    public void generate(int number) {
        this.number = number;
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
            first = getRandomScore();//第一次投球
            if (first == 10) {//全中
                eachTime[2 * i] = 10;
                conditions[2 * i] = Condition.Strike;
                if (i != 9) {//第十轮外全中不进行第二局
                    conditions[2 * i + 1] = Condition.NULL;
                    continue;
                }
            } else //未全中或犯规
                recordMissAndFoul(first, 2 * i);
            second = getRandomScore();//第二次投球
            if (second == 10) {//补中
                eachTime[2 * i + 1] = 10;
                conditions[2 * i + 1] = Condition.Spare;
            } else //未补中或犯规
                recordMissAndFoul(second, 2 * i + 1);
            if (conditions[18] == Condition.Strike || conditions[19] == Condition.Spare)//如果第十局全中或补中要开启第三局
                recordMissAndFoul(getRandomScore(), 20);
        }
    }

    //随机生成-1到10的整数，-1表示犯规
    private static int getRandomScore() {
        return (int) Math.floor(Math.random() * 12 - 1);
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
                            eachRound[i] = eachTime[2 * i] + eachTime[2 * i + 1];
                    }
                }
            }
        }
    }

    //计算全中或补中后两次或一次有效投球的倒瓶数之和（即全中和补中的分数）
    private static void calculateStrikeAndSpare(int round, int time, int count) {
        eachRound[round] = 10;
        for (int i = 0, j = 1; i < count; ++j) {
            if (conditions[time + j] != Condition.NULL) {
                eachRound[round] += eachTime[time + j];
                ++i;
            }
        }
    }

    //返回比赛记录列表
    public ArrayList<String> getBowlingRecord() {
        ArrayList<String> bowlingRecord = new ArrayList<>();
        StringBuffer record = new StringBuffer();
        for (int i = 0; i < 21; ++i) {
            if (conditions[i] != Condition.NULL) {
                if (i == 20)
                    record.append(number + "号选手第10轮第3次投球" + conditions[i]);
                else
                    record.append(number + "号选手第" + (i / 2 + 1) + "轮第" + (i % 2 + 1) + "次投球" + conditions[i]);
                if (conditions[i] == Condition.Miss) //部分命中要说明命中数量
                    record.append(eachTime[i]);
                record.append('\n');
                bowlingRecord.add(record.toString());
                record.setLength(0);//清空字符串
            }
        }
        return bowlingRecord;
    }

    //每一轮的得分
    public int[] getEachRound() {
        return eachRound;
    }

    //每一局的总分
    public int getTotalScore() {
        int sum = 0;
        for (int score : eachRound)
            sum += score;
        return sum;
    }
}