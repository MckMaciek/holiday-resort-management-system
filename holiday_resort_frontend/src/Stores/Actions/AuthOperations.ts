import makeAction from '../ActionMaker/ActionMaker';
import AuthOperationTypes from '../ActionTypes/AuthOperationTypes';

export const loginAction = makeAction(AuthOperationTypes.LOGIN)('payload');
export const registerAction = makeAction(AuthOperationTypes.REGISTER)('payload');