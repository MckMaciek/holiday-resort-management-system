package holiday_resort.management_system.com.holiday_resort.Dto;

import holiday_resort.management_system.com.holiday_resort.Entities.Accommodation;
import holiday_resort.management_system.com.holiday_resort.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AccommodationDTO {

    private Long id;
    private Long numberOfPeople;
    private ResortObjectDTO resortObject;
    private List<EventDTO> eventDTOS;
    private User user;

    public AccommodationDTO(Accommodation accommodation){
        this.id = accommodation.getId();
        this.numberOfPeople = accommodation.getNumberOfPeople();
        this.resortObject = new ResortObjectDTO(accommodation.getResortObject());
        this.user = accommodation.getUser();
        this.eventDTOS = accommodation.getUserEventList().stream().map(EventDTO::new).collect(Collectors.toList());
    }

    public AccommodationDTO(){}
}
