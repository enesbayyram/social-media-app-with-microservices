import type { AxiosResponse } from "axios";
import axios from "../config/AxiosInstance";
import { SM_SECURITY_MANAGER_PATH, SM_USER_MANAGER_PATH } from "../constants/GlobalConstants";
import type { JWT } from "../types/JWT";
import type { RefreshTokenRequest } from "../types/RefreshTokenRequest";
import type { RootEntity } from "../types/RootEntity";
import type { AuthRequest } from "../types/AuthRequest";
import type { UserDef } from "../types/UserDef";

class AuthService {


    authenticate = (request: AuthRequest): Promise<RootEntity<JWT>> => {
        return new Promise((resolve: any, reject: any) => {
            axios.post(`${SM_SECURITY_MANAGER_PATH}/authenticate`, request)
                .then((response: AxiosResponse<any, any>) => resolve(response.data))
                .catch((error: any) => reject(error));
        });
    }


    refreshToken(request: RefreshTokenRequest): Promise<RootEntity<JWT>> {
        return new Promise((resolve: any, reject: any) => {
            axios.post(`${SM_SECURITY_MANAGER_PATH}/refreshToken`, request)
                .then((response: AxiosResponse<any, any>) => resolve(response.data))
                .catch((error: any) => reject(error));
        });
    }

    register(request: FormData): Promise<RootEntity<UserDef>> {
        return new Promise((resolve: any, reject: any) => {
            axios.post(`${SM_SECURITY_MANAGER_PATH}/register`, request)
                .then((response: AxiosResponse<any, any>) => resolve(response.data))
                .catch((error: any) => reject(error));
        });
    }


}

export default new AuthService();