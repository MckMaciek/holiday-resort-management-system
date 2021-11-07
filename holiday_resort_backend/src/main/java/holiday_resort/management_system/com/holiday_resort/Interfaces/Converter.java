package holiday_resort.management_system.com.holiday_resort.Interfaces;

public interface Converter<DTOClass, Object> {
    DTOClass convert(Object requestClass);
}
