package top.arron206.controller.GUIController;

import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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
    //比赛中的面板
    Pane conduct;
    @FXML
    //比赛进行中的信息
    Label conductInfo;

    @FXML
    FlowPane Group;


    @FXML
    protected void toNext(ActionEvent event) {

        //判断是否确认成员信息
        if(state==0 && MemberInfoControl.isConfirm!=1){
            Alert information = new Alert(Alert.AlertType.INFORMATION,"请先确定成员信息！！");
            information.setTitle("提示");
            information.setHeaderText("比赛未开始：");
            information.showAndWait();
            return;
        }

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
            case 8: loadfx("view/Match/MatchGroup.fxml"); insertGroup(); break; //toGroup
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
            setAnimation();
            conductInfo.setText(infoConst);
        };

        EventHandler<ActionEvent> eventHandler2= e -> {
            toNext(new ActionEvent());
        };

        //javafx动画
        Timeline animation=new Timeline(new KeyFrame(Duration.millis(0),eventHandler),new KeyFrame(Duration.millis(3000),eventHandler2));
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

    void insertGroup(){
        for (int i = 0 ;i<60;i++){
            GridPane in = new GridPane();
            in.setStyle("-fx-pref-width: 420;-fx-pref-height: 60; -fx-background-color: rgba(0,0,0,0.1);-fx-font-size: 16");
            in.setVgap(5);
            in.setHgap(5);
            in.setAlignment(Pos.CENTER);
            in.add(new Label("id: "),0,0);
            in.add(new Label("123"),1,0);
            in.add(new Label("姓名: "),2,0);
            in.add(new Label("abc"),3,0);
            in.add(new Label("成绩: "),0,1);
            in.add(new Label("123"),1,1);
            Group.getChildren().add(in);
        }
    }

    void setAnimation(){
        //放置球
        ImageView ball = new ImageView("src/top/arron206/resources/ball.png");
        ball.setFitHeight(60);
        ball.setFitWidth(60);
        ball.setX(60);
        ball.setY(280);
        conduct.getChildren().add(ball);
        //放置瓶子
        ImageView [] bottle = new ImageView[10];
        int [] y = {280,250,310,220,280,340,190,250,310,370};
        int [] x = {700,720,720,740,740,740,780,780,780,780};
        for(int i=0;i<10;i++){
            bottle[i] = new ImageView("src/top/arron206/resources/bottle.png");
            bottle[i].setFitWidth(12);
            bottle[i].setFitHeight(50);
            bottle[i].setX(x[i]);
            bottle[i].setY(y[i]);
            conduct.getChildren().add(bottle[i]);
        }

        //设置动画


        RotateTransition rotateTransition =
                new RotateTransition(Duration.millis(3000),ball);
        rotateTransition.setByAngle(360f);
        rotateTransition.setCycleCount(Timeline.INDEFINITE);
        
        ParallelTransition parallelTransition=new ParallelTransition(rotateTransition);
        parallelTransition.setCycleCount(Timeline.INDEFINITE);
        parallelTransition.play();


    }

}
