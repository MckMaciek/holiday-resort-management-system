package holiday_resort.management_system.com.holiday_resort.Requests;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {

    private Long id;
    private BigDecimal price;
}
