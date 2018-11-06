import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Fpr360SharedModule } from 'app/shared';
import {
    MerchantAccountComponent,
    MerchantAccountDetailComponent,
    MerchantAccountUpdateComponent,
    MerchantAccountDeletePopupComponent,
    MerchantAccountDeleteDialogComponent,
    merchantAccountRoute,
    merchantAccountPopupRoute
} from './';

const ENTITY_STATES = [...merchantAccountRoute, ...merchantAccountPopupRoute];

@NgModule({
    imports: [Fpr360SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MerchantAccountComponent,
        MerchantAccountDetailComponent,
        MerchantAccountUpdateComponent,
        MerchantAccountDeleteDialogComponent,
        MerchantAccountDeletePopupComponent
    ],
    entryComponents: [
        MerchantAccountComponent,
        MerchantAccountUpdateComponent,
        MerchantAccountDeleteDialogComponent,
        MerchantAccountDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Fpr360MerchantAccountModule {}
