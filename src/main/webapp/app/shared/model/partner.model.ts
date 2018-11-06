import { IIndustry } from 'app/shared/model//industry.model';
import {Organization} from 'app/shared/model/organization.model';

export const enum PartnerType {
    REFERRAL = 'REFERRAL',
    RESELLER = 'RESELLER',
    HOLDING_COMPANY = 'HOLDING_COMPANY'
}

export interface IPartner extends Organization {
    type?: PartnerType;
}

export class Partner implements IPartner {
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
        public type?: PartnerType,
        public industry?: IIndustry
    ) {
        this.active = this.active || false;
    }
}
