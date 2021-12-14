package holiday_resort.management_system.com.holiday_resort.Responses;

import holiday_resort.management_system.com.holiday_resort.Dto.AccommodationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationResponse {

    private Long id;
    private Long numberOfPeople;

    private List<EventResponse> eventResponseList;
    private ResortObjectResponse resortObject;

    public AccommodationResponse(AccommodationDTO accommodationDTO){
        this.id = accommodationDTO.getId();
        this.numberOfPeople = accommodationDTO.getNumberOfPeople();
        this.resortObject = new ResortObjectResponse(accommodationDTO.getResortObject());
        this.eventResponseList = accommodationDTO.getEventDTOS().stream().map(EventResponse::new).collect(Collectors.toList());
    }
}
