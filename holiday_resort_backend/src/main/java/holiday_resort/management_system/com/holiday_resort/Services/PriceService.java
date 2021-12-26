package holiday_resort.management_system.com.holiday_resort.Services;

import holiday_resort.management_system.com.holiday_resort.Entities.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PriceService {

    public BigDecimal calculateFinalPrice(Reservation reservation){

        BigDecimal accommodationPrice = calculateAccommodations(reservation.getAccommodationList());
        BigDecimal externalServicesPrice = calculateExternalServices(reservation.getExternalServiceList());

        return accommodationPrice.add(externalServicesPrice);
    }

    private BigDecimal calculateExternalServices(List<ExternalService> externalServiceList){

        BigDecimal price = externalServiceList.stream()
                .reduce(BigDecimal.ZERO, (subTotal, externalService) -> {

                    Long amountOfPeople = externalService.getAmountOfPeople();
                    return subTotal.add(externalService.getServiceRequest().getCost().multiply(BigDecimal.valueOf(amountOfPeople)));

                }, BigDecimal::add);

        return price;
    }


    private BigDecimal calculateAccommodations(List<Accommodation> accommodationList){

        BigDecimal price = accommodationList.stream()
                .reduce(BigDecimal.ZERO, (subTotal, accommodation) -> {

            ResortObject rO = accommodation.getResortObject();
            return subTotal.add(calculateAccommodationPrice(accommodation.getNumberOfPeople(), rO, accommodation));

        }, BigDecimal::add);


        return price;
    }

    private BigDecimal calculateAccommodationPrice(Long numberOfPeople, ResortObject rO, Accommodation accommodation){

        if(rO == null) return BigDecimal.ZERO;

        Long amountOfLessPeople = rO.getMaxAmountOfPeople() - numberOfPeople;

        BigDecimal pricePerPerson = rO.getPricePerPerson();
        BigDecimal pricePerUnusedSpace = rO.getUnusedSpacePrice();

        BigDecimal accommodationPrice = pricePerPerson.multiply(BigDecimal.valueOf(numberOfPeople)).add(BigDecimal.valueOf(amountOfLessPeople).multiply(pricePerUnusedSpace));
        BigDecimal eventPrice =  accommodation.getUserEventList()
                .stream()
                .map(Event::getPrice)
                .reduce(BigDecimal.ZERO, (subtotal, eventP) -> eventP.add(subtotal));


        return accommodationPrice.add(eventPrice);
    }


}
