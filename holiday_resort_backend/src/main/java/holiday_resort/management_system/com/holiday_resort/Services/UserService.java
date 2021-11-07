package holiday_resort.management_system.com.holiday_resort.Services;

import holiday_resort.management_system.com.holiday_resort.Entities.User;
import holiday_resort.management_system.com.holiday_resort.Interfaces.CrudOperations;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Validate;
import holiday_resort.management_system.com.holiday_resort.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService implements CrudOperations<User,Long>, Validate<User> {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository _userRepository){
        this.userRepository = _userRepository;
    }

    @Override
    public List<User> getAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return userRepository.findById(aLong);
    }

    @Override
    public void add(User user) {
        if(!Objects.isNull(user)){
            userRepository.save(user);
        }
    }

    @Override
    public Boolean delete(Long aLong) {

        if(userRepository.findById(aLong).isEmpty()){
            return Boolean.FALSE;
        }
        userRepository.deleteById(aLong);
        return Boolean.TRUE;
    }

    @Override
    public boolean validate(User user) {
        boolean userValidated = true;


        if(Objects.isNull(user.getEmail())) userValidated = false;
        if(Objects.isNull(user.getFirstName())) userValidated = false;
        if(Objects.isNull(user.getLastName())) userValidated = false;
        if(Objects.isNull(user.getPhoneNumber())) userValidated = false;

        return userValidated;
    }
}
