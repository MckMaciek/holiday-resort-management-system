package holiday_resort.management_system.com.holiday_resort.Dto;

import holiday_resort.management_system.com.holiday_resort.Entities.Accommodation;
import holiday_resort.management_system.com.holiday_resort.Entities.Reservation;
import holiday_resort.management_system.com.holiday_resort.Entities.ReservationRemarks;
import holiday_resort.management_system.com.holiday_resort.Entities.User;

import java.math.BigDecimal;
import java.util.List;

public class ReservationDTO {

    private Long id;
    private BigDecimal finalPrice;
    private List<ReservationRemarks> reservationRemarks;
    private List<Accommodation> accommodationList;
    private User user;

    public ReservationDTO(Reservation reservation){
        this.id = reservation.getId();
        this.finalPrice = reservation.getFinalPrice();
        this.accommodationList = reservation.getAccommodationList();
        this.user = reservation.getUser();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public List<ReservationRemarks> getReservationRemarks() {
        return reservationRemarks;
    }

    public void setReservationRemarks(List<ReservationRemarks> reservationRemarks) {
        this.reservationRemarks = reservationRemarks;
    }

    public List<Accommodation> getAccommodationList() {
        return accommodationList;
    }

    public void setAccommodationList(List<Accommodation> accommodationList) {
        this.accommodationList = accommodationList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
