import {LoginActionInterface} from '../../Interfaces/AuthOperations';

const makeAction = (type : any) => {
    
    return (...argsNames : string[]) =>{

        return (...args : any)  => {    //HAS TO BE ANY!

            const action : any  = {type};

            argsNames.forEach((arg : any, index : number) => {
                action[argsNames[index]] = args[index];
            });


            return action;    
        }
    }
} 

export default makeAction;