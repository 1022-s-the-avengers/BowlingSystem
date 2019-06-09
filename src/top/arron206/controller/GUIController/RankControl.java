package top.arron206.controller.GUIController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.List;

public class RankControl {
    @FXML
    private Pane mainPane ;

    @FXML
    //点击获取排名按钮的响应函数
    protected  void showRank(ActionEvent event){
        Button but = (Button) event.getSource();
        String butText = but.getText();
        getRank(butText);
    }

    //获取最新的排名信息
    void getRank(String text){
        switch (text){
            case "单人赛排名" :;break;
            case "双人赛排名" :;break;
            case "三人赛排名" :;break;
            case "五人赛排名" :;break;
            case "精英赛排名" :;break;
        }
    }

}
