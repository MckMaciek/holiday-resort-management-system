package holiday_resort.management_system.com.holiday_resort.Services;

import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Interfaces.LoginDetailsLinked;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GenericAction<ContextClass, ContextClassRepo> {

    public GenericAction(){}

    @SuppressWarnings("unchecked")
    public Pair<LoginDetails, ContextClass> getAssociatedUser(ContextClassRepo contextClassRepo, Long id){

        if(contextClassRepo instanceof CrudRepository<?, ?>){
            CrudRepository<? extends ContextClass, Long> crudRepository =
                    (CrudRepository<? extends ContextClass, Long>) contextClassRepo;


            Optional<? extends ContextClass> associatedEntity = crudRepository.findById(id);

            if(associatedEntity.isEmpty()) throw new IllegalArgumentException(
                    String.format("Id of %s has not been found", id));

            if(!(associatedEntity.get() instanceof LoginDetailsLinked)){
                throw new ClassCastException("Invalid cast");
            }

            LoginDetailsLinked loginDetailsLinked = (LoginDetailsLinked) associatedEntity.get();
            return Pair.of(loginDetailsLinked.getLinkedLoginDetails(), associatedEntity.get());
        }

        else throw new ClassCastException("Invalid cast");
    }

    public boolean checkIfOwnerAndUserRequestAreSame(LoginDetails eventOwner, LoginDetails requestUser){
        return eventOwner.getUsername().equals(requestUser.getUsername());
    }
}
