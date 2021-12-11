package holiday_resort.management_system.com.holiday_resort.Responses;

import holiday_resort.management_system.com.holiday_resort.Dto.AccommodationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationResponse {

    private Long id;
    private Long numberOfPeople;
    private ResortObjectResponse resortObject;

    public AccommodationResponse(AccommodationDTO accommodationDTO){
        this.id = accommodationDTO.getId();
        this.numberOfPeople = accommodationDTO.getNumberOfPeople();
        this.resortObject = new ResortObjectResponse(accommodationDTO.getResortObject());
    }
}
