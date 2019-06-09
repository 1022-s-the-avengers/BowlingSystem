package top.arron206.controller.GUIController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import top.arron206.Main;
import top.arron206.model.Member;

import java.net.URL;
import java.util.List;

public class RankControl {
    @FXML
    private Pane mainPane ;

    @FXML
    private FlowPane rankShow;

    @FXML
    //点击获取排名按钮的响应函数
    protected  void showRank(ActionEvent event){
        Button but = (Button) event.getSource();
        String butText = but.getText();
        getRank(butText);
    }

    //获取最新的排名信息，并加载排名页面
    void getRank(String text){
        switch (text){
            case "单人赛排名" :;break;
            case "双人赛排名" :;break;
            case "三人赛排名" :;break;
            case "五人赛排名" :;break;
            case "精英赛排名" :;break;
        }
        //清空mainPane，加载排名页面
        mainPane.getChildren().clear();

        try {
            URL location = Main.class.getResource("view/Rank/ShowRank.fxml");
            FXMLLoader load = new FXMLLoader();
            load.setController(this);
            load.setLocation(location);
            Parent conduct = load.load();
            //插入排名
            insertRank(text);
            mainPane.getChildren().add(conduct);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void insertRank(String text){
        Label title = new Label(text);
        title.setPrefWidth(900);
        title.setPrefHeight(80);
        title.setAlignment(Pos.CENTER);
        title.setFont(Font.font("Timer New Roman", FontWeight.BOLD, FontPosture.ITALIC, 37));
        rankShow.getChildren().add(title);

        for (int i=0;i<60;i++){
            GridPane insert = new GridPane();
            insert.setStyle("-fx-pref-width: 420;-fx-pref-height: 60; -fx-background-color: rgba(255,255,255,0.6);-fx-font-size: 16");
            insert.setVgap(5);
            insert.setHgap(5);
            insert.setAlignment(Pos.CENTER);
            insert.add(new Label("id: "),0,0);
            insert.add(new Label(i+""),1,0);
            insert.add(new Label("姓名: "),3,0);
            insert.add(new Label("sbjava"),4,0);
            insert.add(new Label("分数: "),0,1);
            insert.add(new Label(108+""),1,1);
            rankShow.getChildren().add(insert);
        }
    }

    @FXML

    void back(ActionEvent event){
        mainPane.getChildren().clear();
        try {
            URL location = Main.class.getResource("view/Rank/ChoseRank.fxml");
            FXMLLoader load = new FXMLLoader();
            load.setController(this);
            load.setLocation(location);
            Parent conduct = load.load();
            mainPane.getChildren().add(conduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
