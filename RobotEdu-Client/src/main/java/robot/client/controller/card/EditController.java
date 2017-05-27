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
import robot.client.common.DataOp;
import robot.client.dao.CardInfoDao;
import robot.client.model.CardTypeModel;
import robot.client.model.card.DataEduCardInfo;
import robot.client.model.card.EduCardInfo;
import robot.client.model.EnableModel;
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

    private DataEduCardInfo dataEduCardInfo;

    public EditController() {
        this.attach(new CardApi());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cmbCardType.setItems(CardTypeModel.getItemList());
        cmbEnable.setItems(EnableModel.getItemList());
        cmbEnable.getSelectionModel().select(0);
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

        if (this.dataEduCardInfo == null) {
            insert(cardInfo);
        } else {
            modify(cardInfo);
        }
        this.close();
    }

    /**
     * 保存数据到数据库
     */
    private void insert(EduCardInfo cardInfo) {
        cardInfo.setCreateTime(System.currentTimeMillis());
        cardInfo.setUpdateTime(cardInfo.getCreateTime());

        // insert to db
        Integer ret = CardInfoDao.insert(cardInfo);
        if (ret > 0) {
            // upload
            cardInfo.setId(ret);
            this.nodifyObservers(cardInfo, DataOp.INSERT);
        }
    }

    private void modify(EduCardInfo cardInfo) {
        cardInfo.setId(this.dataEduCardInfo.getId());
        cardInfo.setUpdateTime(System.currentTimeMillis());

        // update to db
        Integer ret = CardInfoDao.update(cardInfo);
        if (ret > 0) {
            // upload
            this.nodifyObservers(cardInfo, DataOp.MODIFY);
        }
    }


    @FXML
    private void buttonCancelClick(MouseEvent event) {
        close();
    }


    private void loadEduCardInfo() {
        txtCardNo.setText(String.valueOf(this.dataEduCardInfo.getCardNo()));
        for (CardTypeModel model : CardTypeModel.getItemList()) {
            if (model.getValue().equals(this.dataEduCardInfo.getCardType())) {
                cmbCardType.getSelectionModel().select(model);
                break;
            }
        }

        txtTotalTimes.setText(String.valueOf(this.dataEduCardInfo.getTotalTimes()));
        txtPrice.setText(String.valueOf(this.dataEduCardInfo.getPrice()));
        txtDiscount.setText(String.valueOf(this.dataEduCardInfo.getDiscount()));
        txtAdviser.setText(this.dataEduCardInfo.getAdviser());
        for (EnableModel model : EnableModel.getItemList()) {
            if (model.getText().equals(this.dataEduCardInfo.getCardType())) {
                cmbEnable.getSelectionModel().select(model);
                break;
            }
        }
    }

    /**
     * close this window
     */
    private void close() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public DataEduCardInfo getDataEduCardInfo() {
        return dataEduCardInfo;
    }

    public void setDataEduCardInfo(DataEduCardInfo dataEduCardInfo) {
        this.dataEduCardInfo = dataEduCardInfo;
        this.loadEduCardInfo();
    }
}
