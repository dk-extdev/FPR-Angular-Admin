import { IClient } from 'app/shared/model//client.model';
import { IUser } from 'app/core/user/user.model';

export interface IClientUser {
    id?: number;
    client?: IClient;
    user?: IUser;
}

export class ClientUser implements IClientUser {
    constructor(public id?: number, public client?: IClient, public user?: IUser) {}
}
