package holiday_resort.management_system.com.holiday_resort.Services;

import holiday_resort.management_system.com.holiday_resort.Entities.LoginUser;
import holiday_resort.management_system.com.holiday_resort.Entities.UserRoles;
import holiday_resort.management_system.com.holiday_resort.Enums.Roles;
import holiday_resort.management_system.com.holiday_resort.Interfaces.CrudOperations;
import holiday_resort.management_system.com.holiday_resort.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService implements CrudOperations<UserRoles, Long> {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public void assignRolesAndOverride(LoginUser loginUser, Roles... rolesEnum){

        if(loginUser != null) {

            UserRoles userRoles = loginUser.getRoles();
            if (userRoles == null) {
                userRoles = new UserRoles();
                userRoles.setLoginUser(loginUser);
            }
            userRoles.setRolesList(Arrays.asList(rolesEnum));
            loginUser.setRoles(userRoles);
        }
    }

    public void assignRole(LoginUser loginUser, Roles... rolesEnum){

        if(loginUser != null) {

            UserRoles userRoles = loginUser.getRoles();
            if (userRoles == null) {
                userRoles = new UserRoles();
                userRoles.setLoginUser(loginUser);
                userRoles.setRolesList(Arrays.asList(rolesEnum));
            } else {
                List<Roles> userExistingRoles = userRoles.getRolesList();
                userExistingRoles.addAll(Arrays.asList(rolesEnum));

                userRoles.setRolesList(userExistingRoles
                        .stream()
                        .distinct()
                        .collect(Collectors.toList()));
            }
            loginUser.setRoles(userRoles);
        }
    }

    public void saveRole(UserRoles userRoles){
        roleRepository.save(userRoles);
    }

    @Override
    public List<UserRoles> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<UserRoles> findById(Long aLong) {
        return roleRepository.findById(aLong);
    }

    @Override
    public void add(UserRoles userRole) {
        roleRepository.save(userRole);
    }

    @Override
    public Boolean delete(Long aLong) {
        Optional<UserRoles> userRoles = roleRepository.findById(aLong);
        if(userRoles.isPresent()){
            roleRepository.delete(userRoles.get());
            return true;
        }
        return false;
    }

}
