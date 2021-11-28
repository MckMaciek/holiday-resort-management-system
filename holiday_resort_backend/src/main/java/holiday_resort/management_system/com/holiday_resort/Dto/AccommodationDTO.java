package holiday_resort.management_system.com.holiday_resort.Dto;

import holiday_resort.management_system.com.holiday_resort.Entities.Accommodation;
import holiday_resort.management_system.com.holiday_resort.Entities.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AccommodationDTO {

    private Long id;
    private Long numberOfPeople;
    private ResortObjectDTO resortObject;
    private User user;

    public AccommodationDTO(Accommodation accommodation){
        this.id = accommodation.getId();
        this.numberOfPeople = accommodation.getNumberOfPeople();
        this.resortObject = new ResortObjectDTO(accommodation.getResortObject());
        this.user = accommodation.getUser();
    }

    public AccommodationDTO(){}
}
