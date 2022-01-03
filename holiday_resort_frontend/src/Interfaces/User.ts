import {ReservationInterface} from "./Reservation";

export interface User {
    id : number,
    firstName : string,
    lastName : string,
    email : string,

    reservationResponses : Array<ReservationInterface>
}