import {EventInterface} from '../Interfaces/Event';


export interface ResortObjectInterface {
    id : number,
    objectName : string,
    objectType : string,
    maxAmountOfPeople : number,
    pricePerPerson : number,
    unusedSpacePrice : number,
    isReserved : boolean,
    eventResponseList : Array<EventInterface>,
}