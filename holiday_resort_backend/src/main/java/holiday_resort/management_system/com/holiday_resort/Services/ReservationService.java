package holiday_resort.management_system.com.holiday_resort.Services;

import holiday_resort.management_system.com.holiday_resort.Dto.AccommodationDTO;
import holiday_resort.management_system.com.holiday_resort.Dto.ReservationDTO;
import holiday_resort.management_system.com.holiday_resort.Dto.ReservationRemarksDTO;
import holiday_resort.management_system.com.holiday_resort.Entities.LoginDetails;
import holiday_resort.management_system.com.holiday_resort.Entities.Reservation;
import holiday_resort.management_system.com.holiday_resort.Interfaces.CrudOperations;
import holiday_resort.management_system.com.holiday_resort.Interfaces.Validate;
import holiday_resort.management_system.com.holiday_resort.Repositories.ReservationRepository;
import holiday_resort.management_system.com.holiday_resort.Requests.ReservationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService implements CrudOperations<ReservationDTO, Long>, Validate<ReservationDTO> {

    private final ReservationRepository reservationRepository;

    private final AccommodationService accommodationService;
    private final ReservationRemarksService reservationRemarksService;
    private final PriceService priceService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              AccommodationService accommodationService,
                              PriceService priceService,
                              ReservationRemarksService reservationRemarksService){
        this.reservationRepository = reservationRepository;
        this.accommodationService = accommodationService;
        this.priceService = priceService;
        this.reservationRemarksService = reservationRemarksService;
    }

    @Transactional
    public void setReservation(LoginDetails loginDetails, ReservationRequest reservationReq){

        if(validateReservationRequest(reservationReq)) throw new IllegalArgumentException("Invalid Reservation Request!");
        if(Objects.isNull(loginDetails.getUser())) throw new NullPointerException("User cannot be null!");

        List<AccommodationDTO> accommodationDTOS =  reservationReq.getAccommodationRequestList()
                .stream()
                .map(accommodationService::convertRequestToDTO)
                .collect(Collectors.toList());

        accommodationDTOS.forEach(accommodationDTO -> accommodationDTO.setUser(loginDetails.getUser()));

        List<ReservationRemarksDTO> reservationRemarksDTOS = reservationReq.getReservationRemarksRequestList()
                .stream()
                .map(reservationRemarksService::convertRequestToDTO)
                .collect(Collectors.toList());

        ReservationDTO reservationDTO = ReservationDTO.builder()
                .accommodationListDTO(accommodationDTOS)
                .reservationRemarks(reservationRemarksDTOS)
                .finalPrice(priceService.calculateFinalPrice())
                .user(loginDetails.getUser())
                .build();

        this.add(reservationDTO);
    }

    @Override
    public List<ReservationDTO> getAll() {
        return reservationRepository.findAll().stream().map(ReservationDTO::new).collect(Collectors.toList());
    }

    @Override
    public Optional<ReservationDTO> findById(Long aLong) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(aLong);
        return reservationOptional.map(ReservationDTO::new);
    }

    @Override
    @Transactional
    public void add(ReservationDTO reservationDTO) {
        if(validate(reservationDTO) && reservationDTO.getId() != null){

            reservationDTO.getAccommodationListDTO().forEach(accommodationService::add);

            reservationDTO.getReservationRemarks().forEach(
                    reservationRemarksDTO -> reservationRemarksDTO.setReservation(reservationDTO));

            reservationDTO.getReservationRemarks().forEach(reservationRemarksService::add);

            Reservation reservation = new Reservation(reservationDTO);
            reservationRepository.save(reservation);
        }
    }

    @Override
    public Boolean delete(Long aLong) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(aLong);
        if(reservationOptional.isPresent()){
            reservationRepository.delete(reservationOptional.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean validate(ReservationDTO reservationDTO) {
        return reservationDTO != null && reservationDTO.getAccommodationListDTO() != null
                && !reservationDTO.getAccommodationListDTO().isEmpty() && reservationDTO.getFinalPrice() != null &&
                    reservationDTO.getUser() != null;
    }

    private boolean validate(Reservation reservation){
        return reservation != null && reservation.getAccommodationList() != null
                && !reservation.getAccommodationList().isEmpty() && reservation.getFinalPrice() != null &&
                reservation.getUser() != null;
    }

    private boolean validateReservationRequest(ReservationRequest reservationRequest){
        return reservationRequest != null && reservationRequest.getAccommodationRequestList() != null &&
                !reservationRequest.getAccommodationRequestList().isEmpty();
    }
}


