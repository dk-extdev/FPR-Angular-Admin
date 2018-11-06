import { IPartner } from 'app/shared/model//partner.model';
import { IMerchantAccount } from 'app/shared/model//merchant-account.model';

export interface IPartnerClient {
    id?: number;
    partner?: IPartner;
    merchantAccount?: IMerchantAccount;
}

export class PartnerClient implements IPartnerClient {
    constructor(public id?: number, public partner?: IPartner, public merchantAccount?: IMerchantAccount) {}
}
