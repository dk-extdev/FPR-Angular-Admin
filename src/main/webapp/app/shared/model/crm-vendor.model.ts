export const enum CrmApiType {
    BASIC = 'BASIC',
    API_KEY = 'API_KEY',
    OAUTH1 = 'OAUTH1',
    OAUTH2 = 'OAUTH2'
}

export interface ICrmVendor {
    id?: number;
    name?: string;
    apiType?: CrmApiType;
    website?: string;
}

export class CrmVendor implements ICrmVendor {
    constructor(public id?: number, public name?: string, public apiType?: CrmApiType, public website?: string) {}
}
