import {createSlice} from "@reduxjs/toolkit";
import {localStorageAction} from "../../../utils/storage";
import {
    ACCESS_TOKEN_STORAGE_KEY,
    PERMISSIONS_STORAGE_KEY,
    REFRESH_TOKEN_STORAGE_KEY,
    USER_STORAGE_KEY
} from "../../../constant";

export interface UserInfo {
    fullName: string;
    userId: number;
    userCode: string;
    roleNames: string[];
    hostId: string;
}

export interface Authorization {
    token: string | null;
    userInfo: UserInfo | null;
    permissions: string[];
    refreshToken: string | null;
}

const initialState = {
    authorization: {
        token: localStorageAction.get(ACCESS_TOKEN_STORAGE_KEY) || null,
        userInfo: localStorageAction.get(USER_STORAGE_KEY) || null,
        permissions: localStorageAction.get(PERMISSIONS_STORAGE_KEY) || [],
        refreshToken: localStorageAction.get(REFRESH_TOKEN_STORAGE_KEY) || null,
    } as Authorization,
};

const AuthSlice = createSlice({
    name: "auth",
    initialState,
    reducers: {
        setAuthorization: (state, action) => {
            state.authorization = action.payload;
            const token = action.payload.token;
            const userInfo = action.payload.userInfo;
            const permissions = action.payload.permissions;
            const refreshToken = action.payload.refreshToken;
            localStorageAction.set(ACCESS_TOKEN_STORAGE_KEY, token);
            localStorageAction.set(USER_STORAGE_KEY, userInfo);
            localStorageAction.set(PERMISSIONS_STORAGE_KEY, permissions);
            localStorageAction.set(REFRESH_TOKEN_STORAGE_KEY, refreshToken);
        },
        removeAuthorization: (state) => {
            state.authorization = {
                token: null,
                userInfo: null,
                permissions: [],
                refreshToken: null,
            };
            localStorageAction.remove(ACCESS_TOKEN_STORAGE_KEY);
            localStorageAction.remove(USER_STORAGE_KEY);
            localStorageAction.remove(PERMISSIONS_STORAGE_KEY);
            localStorageAction.remove(REFRESH_TOKEN_STORAGE_KEY);
        }
    },
});

export const {
    setAuthorization,
    removeAuthorization
} = AuthSlice.actions;

export default AuthSlice.reducer;


