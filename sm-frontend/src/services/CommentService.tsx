import type { AxiosResponse } from "axios";
import axios from "../config/AxiosInstance";
import { SM_COMMENT_MANAGER_PATH } from "../constants/GlobalConstants";
import type { RootEntity } from "../types/RootEntity";
import type { Comments } from "../types/Comments";
import type { ReplyRequest } from "../types/ReplyRequest";

class CommentService {

    findCommentsByPostId(postId: string): Promise<RootEntity<Comments[]>> {
        return new Promise((resolve: any, reject: any) => {
            axios.get(`${SM_COMMENT_MANAGER_PATH}/comments/list/by-postid/${postId}`)
                .then((response: AxiosResponse<any, any>) => resolve(response.data))
                .catch((error: any) => reject(error));
        });
    }

    createComment(request: ReplyRequest): Promise<RootEntity<Comments>> {
        return new Promise((resolve: any, reject: any) => {
            axios.post(`${SM_COMMENT_MANAGER_PATH}/comments/save`, request)
                .then((response: AxiosResponse<any, any>) => resolve(response.data))
                .catch((error: any) => reject(error));
        })
    }

    deleteCommentById(commentId: string): Promise<RootEntity<boolean>> {
        return new Promise((resolve: any, reject: any) => {
            axios.delete(`${SM_COMMENT_MANAGER_PATH}/comments/delete/${commentId}`)
                .then((response: AxiosResponse<any, any>) => resolve(response.data))
                .catch((error: any) => reject(error));
        });
    }

}

export default new CommentService();