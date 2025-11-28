import type { BaseEntity } from "./BaseEntity";

export interface UserDef extends BaseEntity {
    firstName: string;
    lastName: string;
    username: string;
    password: string;
    profilePhoto: string;
}