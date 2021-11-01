export interface LoginResponse {
    jwt : string,
    userId : string,
    username : string,
    email : string,
    roles : Array<string>
};