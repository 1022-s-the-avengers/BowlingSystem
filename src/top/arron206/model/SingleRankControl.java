package top.arron206.model;

import com.sun.org.apache.xerces.internal.dom.ChildNode;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class SingleRankControl {
    @FXML
    private ChildNode Content;
    @FXML
    private Button btnSinglePre;
    @FXML
    private Button btnSingleNext;
    @FXML
    protected void handlePreShow() {
        System.out.println(" Pre btn");
//        Content.
    }
    @FXML
    protected  void handleNextShow() {
        System.out.println("Next btn");
    }
}
