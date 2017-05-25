package robot.client.controller.card;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import robot.client.api.card.CardApi;
import robot.client.dao.CardInfoDao;
import robot.client.model.CardTypeModel;
import robot.client.model.card.EduCardInfo;
import robot.client.model.swipe.EnableModel;
import robot.client.observer.Subject;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Feng on 2017/5/18.
 */
public class EditController extends Subject implements Initializable {
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField txtCardNo;
    @FXML
    private ComboBox cmbCardType;
    @FXML
    private TextField txtTotalTimes;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtDiscount;
    @FXML
    private TextField txtAdviser;
    @FXML
    private ComboBox cmbEnable;

    public EditController() {
        this.attach(new CardApi());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cmbCardType.setItems(CardTypeModel.getItemList());
        cmbEnable.setItems(EnableModel.getItemList());
        cmbEnable.getSelectionModel().select(0);
    }

    /**
     * 保存数据到数据库
     */
    private void insert() {
        CardTypeModel cardTypeModel = (CardTypeModel) cmbCardType.getSelectionModel().getSelectedItem();
        EnableModel enableModel = (EnableModel) cmbEnable.getSelectionModel().getSelectedItem();


        EduCardInfo cardInfo = new EduCardInfo();
        cardInfo.setCardNo(Long.valueOf(txtCardNo.getText()));
        cardInfo.setCardType(cardTypeModel.getValue());
        cardInfo.setTotalTimes(Integer.valueOf(txtTotalTimes.getText()));
        cardInfo.setUsedTimes(0);
        cardInfo.setPrice(Integer.valueOf(txtPrice.getText()));
        cardInfo.setDiscount(Integer.valueOf(txtDiscount.getText()));
        cardInfo.setAdviser(txtAdviser.getText());
        cardInfo.setFlag(enableModel.getValue());
        cardInfo.setCreateTime(System.currentTimeMillis());
        cardInfo.setUpdateTime(cardInfo.getCreateTime());

        // insert to db
        Integer ret = CardInfoDao.insert(cardInfo);
        if (ret > 0) {
            // upload
            cardInfo.setId(ret);
            this.nodifyObservers(cardInfo);
        }
    }

    @FXML
    private void buttonSaveClick(MouseEvent event) {
        if (StringUtils.isEmpty(txtCardNo.getText())) {
            return;
        }
        if (cmbCardType.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        if (StringUtils.isEmpty(txtPrice.getText())) {
            return;
        }
        if (StringUtils.isEmpty(txtAdviser.getText())) {
            return;
        }

        insert();
    }

    @FXML
    private void buttonCancelClick(MouseEvent event) {
        close();
    }

    /**
     * close this window
     */
    private void close() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

}
