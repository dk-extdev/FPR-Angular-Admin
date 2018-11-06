import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Fpr360SharedModule } from 'app/shared';
import {
    CrmVendorComponent,
    CrmVendorDetailComponent,
    CrmVendorUpdateComponent,
    CrmVendorDeletePopupComponent,
    CrmVendorDeleteDialogComponent,
    crmVendorRoute,
    crmVendorPopupRoute
} from './';

const ENTITY_STATES = [...crmVendorRoute, ...crmVendorPopupRoute];

@NgModule({
    imports: [Fpr360SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CrmVendorComponent,
        CrmVendorDetailComponent,
        CrmVendorUpdateComponent,
        CrmVendorDeleteDialogComponent,
        CrmVendorDeletePopupComponent
    ],
    entryComponents: [CrmVendorComponent, CrmVendorUpdateComponent, CrmVendorDeleteDialogComponent, CrmVendorDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Fpr360CrmVendorModule {}
