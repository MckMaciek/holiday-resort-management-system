package holiday_resort.management_system.com.holiday_resort.Requests;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ExpiredToken {

    private final String message = "TOKEN HAS EXPIRED!";
    private UUID tokenUUID;
    private LocalDateTime nowDate;
    private LocalDateTime tokenDate;

}
