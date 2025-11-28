import type { AxiosResponse } from "axios";
import axios from "../config/AxiosInstance";
import { SM_USER_MANAGER_PATH } from "../constants/GlobalConstants";
import type { RootEntity } from "../types/RootEntity";
import type { UserDef } from "../types/UserDef";

class UserService {

    findByUsername(username: string): Promise<RootEntity<UserDef>> {
        return new Promise((resolve: any, reject: any) => {
            axios.get(`${SM_USER_MANAGER_PATH}/user/username/${username}`)
                .then((response: AxiosResponse<any, any>) => resolve(response.data))
                .catch((error: any) => reject(error));
        });
    }
}

export default new UserService();