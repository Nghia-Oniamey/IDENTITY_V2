import {UserInformation} from "../../type/index.t";

export interface AuthorizationState {
    token: string | null;
    userInfo: UserInformation | {};
    permissions: string[];
    refreshToken: string | null;
}