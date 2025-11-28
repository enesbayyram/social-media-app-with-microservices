import type { Axios, AxiosResponse } from "axios";
import axios from "../config/AxiosInstance";
import { SM_POST_MANAGER_PATH } from "../constants/GlobalConstants";
import type { PostLikeRequest } from "../types/PostLikeRequest";
import type { RootEntity } from "../types/RootEntity";
import type { PostLikeStatusResponse } from "../types/PostLikeStatusResponse";

class PostLikeService {

    switchPostLike(request: PostLikeRequest): Promise<RootEntity<boolean>> {
        return new Promise((resolve: any, reject: any) => {
            axios.put(`${SM_POST_MANAGER_PATH}/post-like/switch`, request)
                .then((response: AxiosResponse<any, any>) => resolve(response.data))
                .catch((error: any) => reject(error));
        });
    }

    findPostLikeStatus(postId: string, userDefId: string): Promise<RootEntity<PostLikeStatusResponse>> {
        return new Promise((resolve: any, reject: any) => {
            axios.get(`${SM_POST_MANAGER_PATH}/post-like/post-like-status?postId=${postId}&userDefId=${userDefId}`)
                .then((response: AxiosResponse) => resolve(response.data))
                .catch((error: any) => reject(error));
        });
    }

}

export default new PostLikeService();