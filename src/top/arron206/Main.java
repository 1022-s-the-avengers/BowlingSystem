package top.arron206;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import top.arron206.controller.ScoreSimulation.CompetitionSimulation;
import top.arron206.controller.ScoreSimulation.CompetitionType;

public class Main extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/Main.fxml"));
        Scene scene = new Scene(root,900,600);
        stage.setTitle("保龄球计分系统");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String...args) {
        Application.launch();
//        CompetitionSimulation competitionSimulation = new CompetitionSimulation();
//        competitionSimulation.ordinaryCompetition(CompetitionType.Singles);
//        competitionSimulation.ordinaryCompetition(CompetitionType.Doubles);
//        competitionSimulation.ordinaryCompetition(CompetitionType.Triples);
//        competitionSimulation.ordinaryCompetition(CompetitionType.Penta);
//        competitionSimulation.classicCompetition();
    }
}
