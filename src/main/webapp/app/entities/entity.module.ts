import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { Fpr360ContactInfoModule } from './contact-info/contact-info.module';
import { Fpr360ServiceProviderModule } from './service-provider/service-provider.module';
import { Fpr360IndustryModule } from './industry/industry.module';
import { Fpr360ClientModule } from './client/client.module';
import { Fpr360MerchantModule } from './merchant/merchant.module';
import { Fpr360PartnerModule } from './partner/partner.module';
import { Fpr360GuruClientModule } from './guru-client/guru-client.module';
import { Fpr360ClientUserModule } from './client-user/client-user.module';
import { Fpr360PartnerUserModule } from './partner-user/partner-user.module';
import { Fpr360MerchantAccountModule } from './merchant-account/merchant-account.module';
import { Fpr360CrmVendorModule } from './crm-vendor/crm-vendor.module';
import { Fpr360ClientCrmModule } from './client-crm/client-crm.module';
import { Fpr360PartnerClientModule } from './partner-client/partner-client.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        Fpr360ContactInfoModule,
        Fpr360ServiceProviderModule,
        Fpr360IndustryModule,
        Fpr360ClientModule,
        Fpr360MerchantModule,
        Fpr360PartnerModule,
        Fpr360GuruClientModule,
        Fpr360ClientUserModule,
        Fpr360PartnerUserModule,
        Fpr360MerchantAccountModule,
        Fpr360CrmVendorModule,
        Fpr360ClientCrmModule,
        Fpr360PartnerClientModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Fpr360EntityModule {}
