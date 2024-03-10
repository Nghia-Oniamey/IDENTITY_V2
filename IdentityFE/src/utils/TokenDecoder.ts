import {jwtDecode} from "jwt-decode";
import {DecodedToken, UserInformation} from "../type/index.t";


export const getUserInformation = (token: string): UserInformation => {
    const decoded = jwtDecode<DecodedToken>(token);
    return {
        fullName: decoded.fullName,
        userId: decoded.userId,
        userCode: decoded.userCode,
        rolesName: decoded.rolesName,
        hostId: decoded.host,
    };
}

export const getPermissionsUser = (token: string): string[] => {
    const decoded = jwtDecode<DecodedToken>(token);
    return decoded.rolesCode;
}

export const getExpireTime = (token: string): number => {
    const decoded = jwtDecode<DecodedToken>(token);
    return decoded.exp;
}

export const isTokenExpired = (token: string): boolean => {
    const expireTime = getExpireTime(token);
    return expireTime < Date.now() / 1000;
}