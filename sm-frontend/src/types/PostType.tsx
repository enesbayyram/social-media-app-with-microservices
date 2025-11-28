import type { BaseEntity } from "./BaseEntity";
import type { UserDef } from "./UserDef";

export interface PostType extends BaseEntity {
    content: string;
    userDef: UserDef;
    picture: string;

}