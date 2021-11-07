package holiday_resort.management_system.com.holiday_resort.Entities;


import holiday_resort.management_system.com.holiday_resort.Enums.RoleTypes;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user_roles_tb")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Roles {

    @Id
    private Long id;

    @ElementCollection(fetch=FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<RoleTypes> roleTypesList = new ArrayList<>();

    @OneToOne
    @MapsId
    private LoginDetails loginDetails;
}
