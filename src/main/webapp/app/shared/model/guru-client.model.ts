import { IUser } from 'app/core/user/user.model';
import { IClient } from 'app/shared/model//client.model';

export const enum GuruClientLevel {
    PRIMARY = 'PRIMARY',
    SECONDARY = 'SECONDARY'
}

export interface IGuruClient {
    id?: number;
    level?: GuruClientLevel;
    guru?: IUser;
    client?: IClient;
}

export class GuruClient implements IGuruClient {
    constructor(public id?: number, public level?: GuruClientLevel, public guru?: IUser, public client?: IClient) {}
}
