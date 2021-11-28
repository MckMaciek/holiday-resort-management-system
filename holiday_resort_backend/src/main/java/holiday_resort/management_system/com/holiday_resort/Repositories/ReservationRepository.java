package holiday_resort.management_system.com.holiday_resort.Repositories;

import holiday_resort.management_system.com.holiday_resort.Entities.Reservation;
import holiday_resort.management_system.com.holiday_resort.Entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "reservationRemarks",
                    "accommodationList",
                    "accommodationList.resortObject"
            }
    )
    List<Reservation> findByUser(User user);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "accommodationList.resortObject"
            }
    )
    List<Reservation> findBriefByUser(User user);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "reservationRemarks",
                    "accommodationList",
                    "accommodationList.resortObject"
            }
    )
    Optional<Reservation> findById(Long id);

}
