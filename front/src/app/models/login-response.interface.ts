import { UserInfo } from "./user-info.interface";

export interface LoginResponse {
    accessToken: string;
    user: UserInfo;
}