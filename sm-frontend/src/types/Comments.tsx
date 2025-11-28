import type { BaseEntity } from "./BaseEntity";
import type { PostType } from "./PostType";
import type { UserDef } from "./UserDef";

export interface Comments extends BaseEntity {
    content: string;
    post: PostType;
    userDef: UserDef;
    parentComment: Comments;
    replies: Comments[];

}