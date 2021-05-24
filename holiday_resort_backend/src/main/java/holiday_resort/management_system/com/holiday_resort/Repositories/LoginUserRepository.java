package holiday_resort.management_system.com.holiday_resort.Repositories;

import holiday_resort.management_system.com.holiday_resort.Entities.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginUserRepository extends JpaRepository<LoginUser, Long> {
    LoginUser findByUsername(String username);
}
