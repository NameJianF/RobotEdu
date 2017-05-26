package robot.client.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Feng on 2017/5/25.
 */
public class EnableModel {
    private String value;
    private String text;

    public EnableModel(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static ObservableList<EnableModel> getItemList() {
        return FXCollections.observableArrayList(
                new EnableModel("1", "可用"),
                new EnableModel("0", "不可用")
        );
    }
}
