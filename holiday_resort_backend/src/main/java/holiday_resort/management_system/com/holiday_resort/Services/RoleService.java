package holiday_resort.management_system.com.holiday_resort.Services;

import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Entities.Roles;
import holiday_resort.management_system.com.holiday_resort.Enums.RoleTypes;
import holiday_resort.management_system.com.holiday_resort.Interfaces.CrudOperations;
import holiday_resort.management_system.com.holiday_resort.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService implements CrudOperations<Roles, Long> {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public void assignRolesAndOverride(LoginDetails loginDetails, RoleTypes... roleTypesEnum){

        if(loginDetails != null) {

            Roles roles = loginDetails.getRoles();
            if (roles == null) {
                roles = new Roles();
                roles.setLoginDetails(loginDetails);
            }
            roles.setRoleTypesList(Arrays.asList(roleTypesEnum));
            loginDetails.setRoles(roles);
        }
    }

    public void assignRole(LoginDetails loginDetails, RoleTypes... roleTypesEnum){

        if(loginDetails != null) {

            Roles roles = loginDetails.getRoles();
            if (roles == null) {
                roles = new Roles();
                roles.setLoginDetails(loginDetails);
                roles.setRoleTypesList(Arrays.asList(roleTypesEnum));
            } else {
                List<RoleTypes> userExistingRoles = roles.getRoleTypesList();
                userExistingRoles.addAll(Arrays.asList(roleTypesEnum));

                roles.setRoleTypesList(userExistingRoles
                        .stream()
                        .distinct()
                        .collect(Collectors.toList()));
            }
            loginDetails.setRoles(roles);
        }
    }

    public void saveRole(Roles roles){
        roleRepository.save(roles);
    }

    @Override
    public List<Roles> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Roles> findById(Long aLong) {
        return roleRepository.findById(aLong);
    }

    @Override
    public void add(Roles userRole) {
        roleRepository.save(userRole);
    }

    @Override
    public Boolean delete(Long aLong) {
        Optional<Roles> userRoles = roleRepository.findById(aLong);
        if(userRoles.isPresent()){
            roleRepository.delete(userRoles.get());
            return true;
        }
        return false;
    }

}
