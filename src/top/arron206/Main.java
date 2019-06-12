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
//        MainControl.competitionSimulation.ordinaryCompetition(CompetitionType.Singles);
//        MainControl.competitionSimulation.ordinaryCompetition(CompetitionType.Doubles);
//        MainControl.competitionSimulation.ordinaryCompetition(CompetitionType.Triples);
//        MainControl.competitionSimulation.ordinaryCompetition(CompetitionType.Penta);
//        MainControl.competitionSimulation.classicCompetition();
    }
}
