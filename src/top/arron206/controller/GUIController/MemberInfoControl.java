package top.arron206.controller.GUIController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import top.arron206.Main;
import top.arron206.model.Member;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MemberInfoControl {

    //存储成员信息是否确认的状态
    public static int isConfirm;

    //存储成员信息

    List<Member> info = new ArrayList<>();


    @FXML
    private FlowPane memberShow;

    @FXML
    private Pane mainPane;

    @FXML
    private Button confirm;

    @FXML
    protected void getInfo(ActionEvent event){
        mainPane.getChildren().clear();
        try {
            URL location = Main.class.getResource("view/MemberInfo/MemberInformation.fxml");
            FXMLLoader load = new FXMLLoader();
            load.setController(this);
            load.setLocation(location);
            Parent conduct = load.load();
            //设置成员列表
            setMemberInfo();
            //设置成员面板
            setInfo();
            mainPane.getChildren().add(conduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setMemberInfo(){
        for(int i=0 ; i<60 ;i++){
            info.add(new Member(i,"sbjava","江西","南昌",0));
        }
    }

    void setInfo(){
        memberShow.getChildren().clear();
        for (Member man : info){
            GridPane insert = new GridPane();
            insert.setStyle("-fx-pref-width: 420;-fx-pref-height: 60; -fx-background-color: rgba(0,0,0,0.1);-fx-font-size: 16");
            insert.setVgap(5);
            insert.setHgap(5);
            insert.setAlignment(Pos.CENTER);
            insert.add(new Label("id: "),0,0);
            insert.add(new Label(man.getId()+""),1,0);
            insert.add(new Label("姓名: "),2,0);
            insert.add(new Label(man.getName()),3,0);
            insert.add(new Label("省份: "),0,1);
            insert.add(new Label(man.getProvince()),1,1);
            insert.add(new Label("城市: "),2,1);
            insert.add(new Label(man.getCity()),3,1);
            final int id = man.getId();
            insert.setOnMouseClicked(
                    e->{
                        if(isConfirm==0)
                            changeInfo(id);
                    }
            );
            memberShow.getChildren().add(insert);
        }
    }

    void changeInfo(int i){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);

        //点击的成员信息
        Member man = info.get(i);
        //输入框，按钮定义
        TextField nameField = new TextField(man.getName());
        TextField provinceField = new TextField(man.getProvince());
        TextField cityField = new TextField(man.getCity());
        Button btConfirm = new Button("确认");
        //添加元素
        grid.add(new Label("id: "),0,0);
        grid.add(new Label(man.getId()+""),1,0);
        grid.add(new Label("姓名: "),0,1);
        grid.add(nameField,1,1);
        grid.add(new Label("省份: "),0,2);
        grid.add(provinceField,1,2);
        grid.add(new Label("城市: "),0,3);
        grid.add(cityField,1,3);
        grid.add(btConfirm,1,4);

        Stage stage = new Stage();
        Scene scene = new Scene(grid,400,300);
        stage.setTitle("修改成员信息");
        stage.setScene(scene);
        stage.show();

        //绑定事件
        btConfirm.setOnAction(e->{
            man.setName(nameField.getText());
            man.setProvince(provinceField.getText());
            man.setCity(cityField.getText());
            stage.close();
            setInfo();
        });
    }

    @FXML
    void toConfirm(ActionEvent event){
        isConfirm = 1;
        confirm.setText("信息已确认");
    }

}
