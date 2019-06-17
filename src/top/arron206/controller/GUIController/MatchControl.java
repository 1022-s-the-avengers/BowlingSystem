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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import top.arron206.Main;
import top.arron206.controller.ScoreSimulation.CompetitionType;
import top.arron206.model.CompetitionInfo;
import top.arron206.model.Group;
import top.arron206.model.Member;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    FlowPane Begin;




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
                newThead(1);toConduct();break;
            case 4:
                newThead(2);toConduct();break;
            case 7:
                newThead(3);toConduct();break;
            case 10:
                newThead(4);toConduct();break;
            case 12:
                newThead(5);toConduct();break;
            case 2:
            case 5:
            case 8: loadfx("view/Match/MatchGroup.fxml");insert(); break;
            case 3:
            case 6:
            case 9:loadfx("view/Match/MatchBegin.fxml");insertGroup();break;
            case 11:loadfx("view/Match/MatchBegin.fxml");insert();break; // toBegin();
            default:loadfx("view/Match/MatchEnd.fxml");insert();
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
            setAnimation();
        };

        EventHandler<ActionEvent> eventHandler2= e -> {
            toNext(new ActionEvent());
        };

        //javafx动画
        Timeline animation=new Timeline(new KeyFrame(Duration.millis(0),eventHandler),new KeyFrame(Duration.millis(30000),eventHandler2));
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

    void insert(){
        String match = new String();

        switch(state){
            case 2 : match = "单人赛";break;
            case 5 : match = "双人赛";break;
            case 8 : match = "三人赛";break;
            case 11: match = "五人赛";break;
            case 12: match = "精英赛";
        }


        for (int i = 0 ;i<60;i++){
            GridPane in = new GridPane();
            in.setStyle("-fx-pref-width: 420;-fx-pref-height: 60; -fx-background-color: rgba(0,0,0,0.1);-fx-font-size: 16");
            in.setVgap(5);
            in.setHgap(5);
            in.setAlignment(Pos.CENTER);
            in.add(new Label (match),1,0);
            in.add(new Label("id: "),0,1);
            in.add(new Label(i+1+""),1,1);
            in.add(new Label("姓名: "),2,1);
            in.add(new Label(MemberInfoControl.info.get(i).getName()),3,1);
            final int num = i;
            final String matchInfo = match;
            in.setOnMouseClicked(
                    e->{
                        showDetail(num,matchInfo);
                    }
            );
            if(state==11)
                Begin.getChildren().add(in);
            else
                Group.getChildren().add(in);
        }
    }

    void setAnimation(){
        //放置球
        ImageView ball = new ImageView("src/top/arron206/resources/ball.png");
        ball.setFitHeight(150);
        ball.setFitWidth(150);
        ball.setX(450);
        ball.setY(220);
        conduct.getChildren().add(ball);

        //设置动画

        //球旋转
        RotateTransition rotateTransition =
                new RotateTransition(Duration.millis(1000),ball);
        rotateTransition.setByAngle(360f);
        rotateTransition.setCycleCount(Timeline.INDEFINITE);

        System.out.println("球转动");
        ParallelTransition parallelTransition=new ParallelTransition(rotateTransition);
        parallelTransition.setCycleCount(Timeline.INDEFINITE);
        parallelTransition.play();
    }

    void showDetail(int i,String match){

        List <CompetitionInfo> info = new LinkedList<>();
        MemberInfoControl.info.get(i).getCompetitionInfo(info);
        System.out.println(info);



        Button bt = new Button("确认");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        //信息显示
        Label id = new Label(MemberInfoControl.info.get(i).getId()+"");
        Label name = new Label(MemberInfoControl.info.get(i).getName());

        grid.add(new Label("id"),0,0);
        grid.add(id,1,0);
        grid.add(new Label("姓名："),0,1);
        grid.add(name,1,1);

        int j=0;

        CompetitionType c = CompetitionType.Singles;
        switch(match){
            case "单人赛": c = CompetitionType.Singles;break;
            case "双人赛": c = CompetitionType.Doubles;break;
            case "三人赛": c = CompetitionType.Triples;break;
            case "五人赛": c = CompetitionType.Penta;break;
        }

        for(CompetitionInfo item : info){
            if(item.getCompetitionType().equals(match)){
                String minfo = getResultString(c,MemberInfoControl.info.get(i).getId(),item.getDescription());
                Label text = new Label(minfo);
                grid.add(new Label("比赛详情"),0,j+2);
                grid.add(text,1,j+2);
                j++;
            }
        }

        Stage stage = new Stage();
        Scene scene = new Scene(grid,400,550);
        stage.setTitle("比赛详情");
        stage.setScene(scene);
        stage.show();

        bt.setOnAction(e->{
            stage.close();
        });
    }

    void newThead(int i){
        Thread sing = new ComSimulation();
        ComSimulation.type = i;
        sing.start();
    }

    void showGroup(int num){

        ArrayList <Member> memb = new ArrayList<>();
       String type = new String();

       switch(state){
           case 3 : type="双人赛" ; break;
           case 6 : type="三人赛" ; break;
           case 9 : type="五人赛" ; break;
       }

       Group a= new Group(num+1,type);
        int iii = a.getMembers(memb);

        System.out.println(iii);

        System.out.println(num+type);

        System.out.println(memb);

        Button bt = new Button("确认");

        VBox grad = new VBox();
        grad.setStyle("-fx-pref-width: 420;-fx-pref-height: 60; -fx-background-color: rgba(0,0,0,0.1);-fx-font-size: 16");
        grad.setAlignment(Pos.CENTER);

//        //信息显示
//        Label id = new Label(MemberInfoControl.info.get(i).getId()+"");
//        Label name = new Label(MemberInfoControl.info.get(i).getName());
//
//        grid.add(new Label("id"),0,0);
//        grid.add(id,1,0);
//        grid.add(new Label("姓名："),0,1);
//        grid.add(name,1,1);

//        for(int j=0;j<6;j++){
//            Label text = new Label(info.get(j).getDescription()+"");
//            grid.add(new Label("单人赛"),0,j+2);
//            grid.add(text,1,j+2);
//        }

        for(Member item : memb){
            GridPane in = new GridPane();
            in.setStyle("-fx-pref-width: 500;-fx-pref-height: 60; -fx-background-color: rgba(0,0,0,0.1);-fx-font-size: 16");
            in.setVgap(5);
            in.setHgap(5);
            in.setAlignment(Pos.CENTER);
            Label id = new Label(item.getId()+"");
            Label name = new Label(item.getName()+"");
            in.add(new Label("ID:"),0,0);
            in.add(id,1,0);
            in.add(new Label("姓名："),0,1);
            in.add(name,1,1);
            grad.getChildren().add(in);
        }

        grad.getChildren().add(bt);
        Stage stage = new Stage();
        Scene scene = new Scene(grad,500,560);
        stage.setTitle("分组详情");
        stage.setScene(scene);
        stage.show();

        bt.setOnAction(e->{
            stage.close();
        });
    }

    void insertGroup (){
        int group = 0;
        switch(state){
            case 3 : group=30;break;
            case 6 : group=20;break;
            case 9 : group=12;break;
        }

        for (int i = 0 ;i<group;i++){
            List <CompetitionInfo> info = new LinkedList<>();
            GridPane in = new GridPane();
            in.setStyle("-fx-pref-width: 420;-fx-pref-height: 60; -fx-background-color: rgba(0,0,0,0.1);-fx-font-size: 16");
            in.setVgap(5);
            in.setHgap(5);
            in.setAlignment(Pos.CENTER);
            in.add(new Label("第"+(i+1)+"组"),0,0);
            final int num = i;
            in.setOnMouseClicked(
                    e->{
                        showGroup(num);
                    }
            );
            Begin.getChildren().add(in);
        }


    }

    public String getResultString(CompetitionType competitionType, int number, int description) {
        StringBuffer result = new StringBuffer();
        result.append(number);
        result.append("号选手");
        result.append(competitionType.toString());
        result.append("第");
        result.append(description / 100000);
        description = description % 100000;
        result.append("局第");
        int turn = description / 10000;
        description = description % 10000;
        if (turn == 0)
            turn = 10;
        result.append(turn);
        result.append("轮第");
        result.append(description / 1000);
        description = description % 1000;
        result.append("次投球");
        int status = description / 100;
        result.append(getStatus(status));
        description = description % 100;
        if (status == 2) {
            result.append(description);
            result.append("个");
        }
        return result.toString();
    }

    public String getStatus(int status) {
        switch (status) {
            case 0:
                return "全中";
            case 1:
                return "补中";
            case 2:
                return "命中";
            case 3:
                return "犯规";
            case 4:
                return "未进行";
        }
        return "";
    }

}
