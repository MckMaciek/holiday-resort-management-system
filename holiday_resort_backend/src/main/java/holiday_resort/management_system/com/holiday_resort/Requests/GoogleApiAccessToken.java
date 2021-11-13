package holiday_resort.management_system.com.holiday_resort.Requests;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GoogleApiAccessToken {

    private String access_token;
    private String token_type;
    private String expires_in;
    private String scope;
}
