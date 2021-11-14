package holiday_resort.management_system.com.holiday_resort.Entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="expiry_date_until_activation")
    private LocalDateTime expiryDate;

    @Column(name="activated_date")
    private LocalDateTime activationDate;

    @NotBlank
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    private LoginDetails loginDetails;
}
