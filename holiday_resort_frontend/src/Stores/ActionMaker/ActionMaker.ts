
const makeAction = (actionTypeName : any) => {

    return (...argsNames : any[]) =>{
        return (...args : any) => {    //HAS TO BE ANY!
            const action : any  = {actionTypeName}; //HAS TO BE ANY!
    
            argsNames.forEach((arg : any, index : number) => {
                action[argsNames[index]] = args[index];
            });

            return action;
        }
    }
} 

export default makeAction;