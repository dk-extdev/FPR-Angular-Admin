import { IIndustry } from 'app/shared/model//industry.model';
import {Organization} from 'app/shared/model/organization.model';
import {IClient} from 'app/shared/model/client.model';

export interface IMerchant extends Organization {}

export class Merchant implements IMerchant {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public address?: string,
        public city?: string,
        public state?: string,
        public postalCode?: string,
        public country?: string,
        public phoneNumber?: string,
        public phoneExt?: string,
        public email?: string,
        public website?: string,
        public currency?: string,
        public active?: boolean,
        public industry?: IIndustry,
        public client?: IClient
    ) {
        this.active = this.active || false;
    }
}
