package holiday_resort.management_system.com.holiday_resort.Exceptions;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiErrorException {

    private int httpStatus;
    private LocalDateTime errorTime;
    private String errorMessage;
}
