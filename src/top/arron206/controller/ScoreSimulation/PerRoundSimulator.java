package top.arron206.controller.ScoreSimulation;

public class PerRoundSimulator {
    //每次投球倒瓶数
    private int eachTime[] = new int[21];
    //每次投球情况
    private Condition conditions[] = new Condition[21];
    //每轮投球得分
    private int eachTurn[] = new int[10];
    //每局比赛信息，0是一般描述信息，1是犯规次数，2是本局总分
    private int[] resultArray = new int[3];
    //轮次
    private int round;
    //选手编号
    private int number;

    //产生各种比赛信息
    public void start(int number, int level, int round) {
        this.number = number;
        this.round = round;
        for (int i = 0; i < 21; ++i) {
            eachTime[i] = 0;
            conditions[i] = Condition.NULL;
        }
        for (int i = 0; i < 3; ++i)
            resultArray[i] = 0;
        bowlingBall(level);
        countRoundScore();
    }

    //模拟投球，并记录投球情况
    private void bowlingBall(int level) {
        int first, second;
        for (int i = 0; i < 10; ++i) {
            first = getRandomScore(level);//第一次投球
            if (first == 10) {//全中
                eachTime[2 * i] = 10;
                conditions[2 * i] = Condition.Strike;
                if (i != 9) {//第十轮外全中不进行第二局
                    conditions[2 * i + 1] = Condition.NULL;
                    continue;
                }
            } else //未全中或犯规
                recordMissAndFoul(first, 2 * i);
            second = getRandomScore(level);//第二次投球
            if (second == 10) {//补中
                eachTime[2 * i + 1] = 10;
                conditions[2 * i + 1] = Condition.Spare;
            } else //未补中或犯规
                recordMissAndFoul(second, 2 * i + 1);
            if (conditions[18] == Condition.Strike || conditions[19] == Condition.Spare)//如果第十局全中或补中要开启第三局
                recordMissAndFoul(getRandomScore(level), 20);
        }
    }

    //根据选手实力返回一个随机分数
    private static int getRandomScore(int level) {
        return RandInteger.normalRand(-1, 10, level, 1);
    }

    //处理部分命中或犯规。amount为倒瓶数，time为投球次序数
    private void recordMissAndFoul(int amount, int time) {
        if (amount < 0) //记录犯规，倒瓶数视为0
            conditions[time] = Condition.Foul;
        else {
            eachTime[time] = amount;//倒瓶数
            conditions[time] = Condition.Miss;
        }
    }

    //计算每轮得分
    private void countRoundScore() {
        for (int i = 0; i < 10; ++i) {
            switch (conditions[2 * i]) {//第一次投球
                case Strike://全中
                    eachTurn[i] = 10;
                    for (int j = 0, k = 1; j < 2; ++k) {
                        if (conditions[2 * i + k] != Condition.NULL) {
                            eachTurn[i] += eachTime[2 * i + k];
                            ++j;
                        }
                    }
                    break;
                default: {//第二次投球
                    switch (conditions[2 * i + 1]) {
                        case Spare://补中
                            eachTurn[i] = 10;
                            for (int j = 0, k = 1; j < 1; ++k) {
                                if (conditions[2 * i + 1 + k] != Condition.NULL) {
                                    eachTurn[i] += eachTime[2 * i + 1 + k];
                                    ++j;
                                }
                            }
                            break;
                        default://部分命中或犯规
                            eachTurn[i] = eachTime[2 * i] + eachTime[2 * i + 1];
                    }
                }
            }
        }
    }

    public int[] getResultArray() {
        int turn;
        for (int i = 0; i < 21; ++i) {
            if (conditions[i] != Condition.NULL) {
                if (conditions[i] == Condition.Foul)
                    ++resultArray[1];
                resultArray[0] = round * 100000;
                turn = (i / 2 + 1);
                if (turn != 10) //第几轮
                    resultArray[0] += (turn * 10000);
                if (i != 20)//第几次
                    resultArray[0] += ((i % 2 + 1) * 1000);
                else
                    resultArray[0] += (3 * 1000);
                resultArray[0] += (conditions[i].ordinal() * 100);
                if (conditions[i] == Condition.Miss) //命中数量
                    resultArray[0] += eachTime[i];
            }
        }
        for (int e : eachTurn)
            resultArray[2] += e;
        System.out.println(resultArray[2]);
        return resultArray;
    }

    public int[] getResArray() {
        int[] resArray = new int[12];
        for (int i = 0; i < 10; ++i)
            resArray[i] = eachTurn[i];
        resArray[10] = round;
        resArray[11] = number;
        return resArray;
    }
}