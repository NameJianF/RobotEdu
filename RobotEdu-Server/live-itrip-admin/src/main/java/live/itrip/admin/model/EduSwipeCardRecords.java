package live.itrip.admin.model;

public class EduSwipeCardRecords extends BaseModel{
    private Long id;

    private String shopNo;

    private Integer cardNo;

    private Long clientCreateTime;

    private Long createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo == null ? null : shopNo.trim();
    }

    public Integer getCardNo() {
        return cardNo;
    }

    public void setCardNo(Integer cardNo) {
        this.cardNo = cardNo;
    }

    public Long getClientCreateTime() {
        return clientCreateTime;
    }

    public void setClientCreateTime(Long clientCreateTime) {
        this.clientCreateTime = clientCreateTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}