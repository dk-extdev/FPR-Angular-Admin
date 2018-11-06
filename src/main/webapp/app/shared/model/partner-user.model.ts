import { IPartner } from 'app/shared/model//partner.model';
import { IUser } from 'app/core/user/user.model';

export interface IPartnerUser {
    id?: number;
    partner?: IPartner;
    user?: IUser;
}

export class PartnerUser implements IPartnerUser {
    constructor(public id?: number, public partner?: IPartner, public user?: IUser) {}
}
