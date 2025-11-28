import type { AxiosResponse } from "axios";
import axios from "../config/AxiosInstance";
import { SM_POST_MANAGER_PATH } from "../constants/GlobalConstants";
import type { RootEntity } from "../types/RootEntity";
import type { PostType } from "../types/PostType";

class PostService {

    createPost(formData: FormData): Promise<RootEntity<PostType>> {
        return new Promise((resolve: any, reject: any) => {
            axios.post(`${SM_POST_MANAGER_PATH}/post/save`, formData)
                .then((response: AxiosResponse<any, any>) => resolve(response.data))
                .catch((error: any) => reject(error));
        });
    }

    findAllPost(): Promise<RootEntity<PostType[]>> {
        return new Promise((resolve: any, reject: any) => {
            axios.get(`${SM_POST_MANAGER_PATH}/post/list-all`)
                .then((response: AxiosResponse<any, any>) => resolve(response.data))
                .catch((error: any) => reject(error));
        })
    }

}

export default new PostService();