package holiday_resort.management_system.com.holiday_resort.Model;

import holiday_resort.management_system.com.holiday_resort.Requests.GoogleApiAccessToken;
import org.glassfish.jersey.logging.LoggingFeature;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

@Service
public class GoogleApiModel {

    private static final String GOOGLE_OAUTH_API = "https://oauth2.googleapis.com/token";

    private static final String CLIENT_SECRET_KEY = "client_secret";
    private static final String GRANT_TYPE_KEY = "grant_type";
    private static final String REFRESH_TOKEN_KEY = "refresh_token";
    private static final String CLIENT_ID_KEY = "client_id";

    private static final String CONTENT_LENGTH = "Content-Length";

    private final Client client;

    public GoogleApiModel(){
        client = ClientBuilder.newBuilder()
                .property(LoggingFeature.LOGGING_FEATURE_VERBOSITY_CLIENT, LoggingFeature.Verbosity.PAYLOAD_ANY)
                .property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_CLIENT, "INFO")
                .build();
    }

    public GoogleApiAccessToken fetchGoogleApiAccessToken(
      String clientSecret,
      String grantType,
      String refreshToken,
      String clientId
    ){
            return client
                    .target(GOOGLE_OAUTH_API)
                    .queryParam(CLIENT_SECRET_KEY, clientSecret)
                    .queryParam(GRANT_TYPE_KEY, grantType)
                    .queryParam(REFRESH_TOKEN_KEY, refreshToken)
                    .queryParam(CLIENT_ID_KEY, clientId)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header(CONTENT_LENGTH, 0)
                    .post(Entity.json("{}"), GoogleApiAccessToken.class);
    }
}
