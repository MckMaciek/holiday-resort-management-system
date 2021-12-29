package holiday_resort.management_system.com.holiday_resort.Events;

import holiday_resort.management_system.com.holiday_resort.Dto.ReservationDTO;
import holiday_resort.management_system.com.holiday_resort.Enums.ReservationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@Builder
public class ReservationStatusChangedEvent extends ApplicationEvent {

    private final ReservationDTO reservationDTO;
    private final ReservationStatus previousState;

    public ReservationStatusChangedEvent(ReservationDTO source, ReservationStatus previousState) {
        super(source);

        this.reservationDTO = source;
        this.previousState = previousState;
    }
}
