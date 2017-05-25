package robot.client.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Feng on 2017/5/25.
 */
public class CardTypeModel {
    private String value;
    private String text;

    public CardTypeModel(String value, String text) {
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

    public static ObservableList<CardTypeModel> getItemList() {
        return FXCollections.observableArrayList(
                new CardTypeModel("1", "次卡")
        );
    }
}
