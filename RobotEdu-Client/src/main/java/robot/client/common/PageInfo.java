package robot.client.common;

import javafx.fxml.Initializable;
import javafx.scene.Node;

/**
 * Created by Feng on 2017/5/26.
 */
public class PageInfo {
//    private String clazz;
    private Node node;
    private Initializable controller;

    public Node getNode() {
        return node;
    }

//    public String getClazz() {
//        return clazz;
//    }
//
//    public void setClazz(String clazz) {
//        this.clazz = clazz;
//    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Initializable getController() {
        return controller;
    }

    public void setController(Initializable controller) {
        this.controller = controller;
    }
}
