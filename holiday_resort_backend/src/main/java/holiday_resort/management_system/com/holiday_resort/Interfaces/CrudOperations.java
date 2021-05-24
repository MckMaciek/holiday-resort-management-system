package holiday_resort.management_system.com.holiday_resort.Interfaces;

import java.util.List;
import java.util.Optional;

public interface CrudOperations<T, ID> {

    List<T> getAll();
    Optional<T> findById(ID id);
    void add(T t);

}
