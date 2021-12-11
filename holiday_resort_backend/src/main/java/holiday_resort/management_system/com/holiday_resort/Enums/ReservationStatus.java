package holiday_resort.management_system.com.holiday_resort.Enums;

public enum ReservationStatus {
    FINALIZED, PENDING, ARCHIVED, STARTED, CANCELLED, ACCEPTED // zamiast STARTED dac HEW
}


// na poczatku jest DRAFT
// po wcisnieciu wyslij zmien status na NEW
// jak sie zaloguje admin i widzi tylko NEW w gore
// admin moze zmienic na ACCEPTED lub CANCELLED lub REJECTED
// REJECTED moze edytowac user