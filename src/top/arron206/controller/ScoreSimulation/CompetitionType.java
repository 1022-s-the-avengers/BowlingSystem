package top.arron206.controller.ScoreSimulation;

public enum CompetitionType {
    Singles("单人赛"), Doubles("双人赛"), Triples("三人赛"), Penta("五人赛"), Classic("精英赛");
    String str;

    CompetitionType(String str) {
        this.str = str;
    }

    int getAmount() {
        switch (str) {
            case "单人赛":
                return 1;
            case "双人赛":
                return 2;
            case "三人赛":
                return 3;
            case "五人赛":
                return 5;
            case "精英赛":
                return 6;
        }
        return 0;
    }

    public String toString() {
        return str;
    }
}
