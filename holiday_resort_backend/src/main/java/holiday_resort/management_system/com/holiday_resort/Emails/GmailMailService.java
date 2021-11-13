package holiday_resort.management_system.com.holiday_resort.Emails;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import holiday_resort.management_system.com.holiday_resort.Model.GoogleApiModel;
import holiday_resort.management_system.com.holiday_resort.Requests.GoogleApiAccessToken;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

@Component
public class GmailMailService {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static final String SMTP_AUTH = "mail.smtp.auth";
    private static final String SMTP_START_TLS = "mail.smtp.starttls.enable";
    private static final String SMTP_HOST = "mail.smtp.host";
    private static final String SMTP_PORT = "mail.smtp.port";

    private static final String PORT = "587";
    private static final String HOST = "smtp.gmail.com";

    private static final String APP_NAME = "HolidayResortApp";
    private static final String APP_ADDRESS = "holidayresortapp@gmail.com";

    @Value("${client.secret}")
    private String TEMP_CLIENT_SECRET;
    @Value("${grant.type}")
    private String TEMP_GRANT_TYPE;
    @Value("${refresh.token}")
    private String TEMP_REFRESH_TOKEN;
    @Value("${client.id}")
    private String TEMP_CLIENT_ID;

    private GoogleApiModel googleApiModel;
    private HttpTransport httpTransport;

    @Autowired
    public GmailMailService(
    ) throws GeneralSecurityException, IOException {

        this.googleApiModel = new GoogleApiModel();
        this.httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    }

    public boolean sendMessage(String to, String subject, String body) throws MessagingException, IOException {
        MimeMessage mimeMessage = setMimeMessage(to, subject, body);
        Message message = encodeMime(mimeMessage);

        return createGmail(message);
    }

    private Message encodeMime(MimeMessage mimeMessage) throws IOException, MessagingException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        mimeMessage.writeTo(buffer);

        return new Message()
                .setRaw(Base64.encodeBase64String(buffer.toByteArray()));
    }

    private MimeMessage setMimeMessage(String to, String subject, String body) throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage(Session.getDefaultInstance(provideProperties()));
        mimeMessage.setSubject(subject);
        mimeMessage.setText(body);
        mimeMessage.setFrom(new InternetAddress(APP_ADDRESS));

        mimeMessage.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));

        return mimeMessage;
    }

    private boolean createGmail(Message message) throws IOException {

        GoogleApiAccessToken googleApiAccessToken = googleApiModel.fetchGoogleApiAccessToken(
                TEMP_CLIENT_SECRET,
                TEMP_GRANT_TYPE,
                TEMP_REFRESH_TOKEN,
                TEMP_CLIENT_ID
        );

        Credential credentials = new GoogleCredential.Builder()
                .setJsonFactory(JSON_FACTORY)
                .setTransport(httpTransport)
                .setClientSecrets(TEMP_CLIENT_ID, TEMP_CLIENT_SECRET)
                .build()
                .setAccessToken(googleApiAccessToken.getAccess_token())
                .setRefreshToken(TEMP_REFRESH_TOKEN);


        return new Gmail.Builder(httpTransport, JSON_FACTORY, credentials)
                .setApplicationName(APP_NAME)
                .build()
                .users()
                .messages()
                .send(APP_ADDRESS, message)
                .execute()
                .getLabelIds().contains("SENT");
    }

    private Properties provideProperties(){
        Properties prop = new Properties();
        prop.put(SMTP_AUTH, true);
        prop.put(SMTP_START_TLS, "true");
        prop.put(SMTP_HOST, HOST);
        prop.put(SMTP_PORT, PORT);

        return prop;
    }
}
