export interface ReplyRequest {
    content: string;
    postId: string;
    userDefId: string;
    parentCommentId: string | null;
}