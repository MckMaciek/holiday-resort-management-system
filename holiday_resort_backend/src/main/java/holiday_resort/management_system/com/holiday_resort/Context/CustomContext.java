package holiday_resort.management_system.com.holiday_resort.Context;

import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Interfaces.LoginDetailsLinked;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CustomContext<ContextClass, ContextClassRepo> {

    public CustomContext(){}

    @SuppressWarnings("unchecked")
    public Pair<LoginDetails, ContextClass> getAssociatedUser(ContextClassRepo contextClassRepo, Long id){

        if(contextClassRepo instanceof CrudRepository<?, ?>){
            CrudRepository<? extends ContextClass, Long> crudRepository =
                    (CrudRepository<? extends ContextClass, Long>) contextClassRepo;


            Optional<? extends ContextClass> associatedEntity = crudRepository.findById(id);

            if(associatedEntity.isEmpty()) throw new IllegalArgumentException(
                    String.format("%s with id %s has no owner!", associatedEntity.getClass().toString(), id));

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
