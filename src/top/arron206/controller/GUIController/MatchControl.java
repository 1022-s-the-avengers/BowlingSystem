package top.arron206.controller.GUIController;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import top.arron206.Main;

import java.net.URL;

public class MatchControl {
    /*存储比赛进行状态，
        0 单人赛开始 ，
        1 单人赛进行中 ，
        2 开始双人赛分组 ，
        3 开始双人赛 ，
        4 双人赛进行中，
        5 开始三人赛分组，
        6 开始三人赛，
        7 三人赛进行中，
        8 开始五人赛分组
        9 开始五人赛
        10 五人赛进行中
        11 开始精英赛
        12 精英赛进行中
        13 精英赛结果
      */
    public static int state;

    @FXML
    //比赛的主面板
    private AnchorPane MatchPane;
    @FXML
    //比赛进行中的信息
    Label conductInfo;


    @FXML
    protected void toNext(ActionEvent event) {
        MatchPane.getChildren().clear();
        state++;
        switch (state) {
            case 1:
            case 4:
            case 7:
            case 10:
            case 12: toConduct();break;
            case 2:
            case 5:
            case 8: loadfx("view/Match/MatchGroup.fxml");break; //toGroup
            case 3:
            case 6:
            case 9:
            case 11:loadfx("view/Match/MatchBegin.fxml");break; // toBegin();
            default:loadfx("view/Match/MatchEnd.fxml");
        }

    }


    void toConduct() {
        String info = new String();
        switch (state) {
            case 1:
                info = "单人赛进行中...";
                break;
            case 4:
                info = "双人赛进行中...";
                break;
            case 7:
                info = "三人赛进行中...";
                break;
            case 10:
                info = "五人赛进行中...";
                break;
            case 12:
                info = "精英赛进行中...";
                break;
        }

        final String infoConst = info;

        EventHandler<ActionEvent> eventHandler= e -> {
            loadfx("view/Match/MatchConduct.fxml");
            conductInfo.setText(infoConst);
        };

        EventHandler<ActionEvent> eventHandler2= e -> {
            toNext(new ActionEvent());
        };

        //javafx动画
        Timeline animation=new Timeline(new KeyFrame(Duration.millis(0),eventHandler),new KeyFrame(Duration.millis(2000),eventHandler2));
        animation.setCycleCount(1);
        animation.play();
    }


    //加载FXML文件，并添加到MatchPane中
    void loadfx (String url){
        try {
            URL location = Main.class.getResource(url);
            FXMLLoader load = new FXMLLoader();
            load.setController(this);
            load.setLocation(location);
            Parent conduct = load.load();
            MatchPane.getChildren().add(conduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
