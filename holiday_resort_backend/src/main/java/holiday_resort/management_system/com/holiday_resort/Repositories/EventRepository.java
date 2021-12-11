package holiday_resort.management_system.com.holiday_resort.Repositories;

import holiday_resort.management_system.com.holiday_resort.Entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> getEventById(Long id);
}
