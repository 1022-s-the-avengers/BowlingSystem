package top.arron206.controller.ScoreSimulation;

public enum Condition {
    Strike("全中"), Spare("补中"), Miss("命中"), Foul("犯规"), NULL("未进行");
    String str;
    Condition(String str) {
        this.str = str;
    }
    public String toString() {
        return this.str;
    }
}
