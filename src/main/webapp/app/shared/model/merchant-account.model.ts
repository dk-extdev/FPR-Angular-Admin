import { IMerchant } from 'app/shared/model//merchant.model';
import { IClientCrm } from 'app/shared/model//client-crm.model';

export interface IMerchantAccount {
    id?: number;
    mid?: string;
    midDescriptor?: string;
    active?: boolean;
    merchant?: IMerchant;
    clientCrm?: IClientCrm;
}

export class MerchantAccount implements IMerchantAccount {
    constructor(
        public id?: number,
        public mid?: string,
        public midDescriptor?: string,
        public active?: boolean,
        public merchant?: IMerchant,
        public clientCrm?: IClientCrm
    ) {
        this.active = this.active || false;
    }
}
