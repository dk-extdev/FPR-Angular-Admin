import { IClient } from 'app/shared/model//client.model';
import { ICrmVendor } from 'app/shared/model//crm-vendor.model';

export interface IClientCrm {
    id?: number;
    endpoint?: string;
    webUsername?: string;
    webPassword?: string;
    apiKey?: string;
    apiSecret?: string;
    client?: IClient;
    vendor?: ICrmVendor;
}

export class ClientCrm implements IClientCrm {
    constructor(
        public id?: number,
        public endpoint?: string,
        public webUsername?: string,
        public webPassword?: string,
        public apiKey?: string,
        public apiSecret?: string,
        public client?: IClient,
        public vendor?: ICrmVendor
    ) {}
}
