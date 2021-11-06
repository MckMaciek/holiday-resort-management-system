package holiday_resort.management_system.com.holiday_resort.Dto;

import holiday_resort.management_system.com.holiday_resort.Entities.ResortObject;

import java.math.BigDecimal;

public class ResortObjectDTO {

    private Long id;
    private String objectName;
    private String objectType;
    private Long maxAmountOfPeople;
    private BigDecimal pricePerPerson;
    private BigDecimal unusedSpacePrice;
    private Boolean isReserved;

    public ResortObjectDTO (ResortObject resortObject){
        this.id = resortObject.getId();
        this.objectName = resortObject.getObjectName();
        this.objectType = resortObject.getObjectType();
        this.maxAmountOfPeople = resortObject.getMaxAmountOfPeople();
        this.pricePerPerson = resortObject.getPricePerPerson();
        this.unusedSpacePrice = resortObject.getUnusedSpacePrice();
        this.isReserved = resortObject.getIsReserved();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Long getMaxAmountOfPeople() {
        return maxAmountOfPeople;
    }

    public void setMaxAmountOfPeople(Long maxAmountOfPeople) {
        this.maxAmountOfPeople = maxAmountOfPeople;
    }

    public BigDecimal getPricePerPerson() {
        return pricePerPerson;
    }

    public void setPricePerPerson(BigDecimal pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    public BigDecimal getUnusedSpacePrice() {
        return unusedSpacePrice;
    }

    public void setUnusedSpacePrice(BigDecimal unusedSpacePrice) {
        this.unusedSpacePrice = unusedSpacePrice;
    }

    public Boolean getReserved() {
        return isReserved;
    }

    public void setReserved(Boolean reserved) {
        isReserved = reserved;
    }
}
