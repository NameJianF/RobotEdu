package robot.client.controller;

import robot.client.api.card.CardApi;
import robot.client.model.card.EduCardInfo;
import robot.client.observer.Subject;

/**
 * Created by Feng on 2017/5/18.
 */
public class CardInfoController extends Subject {

    public CardInfoController() {
        this.attach(new CardApi());
    }

    /**
     * 保存数据到数据库
     */
    private void insert() {
        EduCardInfo cardInfo = new EduCardInfo();
        this.nodifyObservers(cardInfo);
    }
}
