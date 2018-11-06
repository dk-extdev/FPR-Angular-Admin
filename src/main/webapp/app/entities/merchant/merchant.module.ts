import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Fpr360SharedModule } from 'app/shared';
import {
    MerchantComponent,
    MerchantDetailComponent,
    MerchantUpdateComponent,
    MerchantDeletePopupComponent,
    MerchantDeleteDialogComponent,
    merchantRoute,
    merchantPopupRoute
} from './';

const ENTITY_STATES = [...merchantRoute, ...merchantPopupRoute];

@NgModule({
    imports: [Fpr360SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MerchantComponent,
        MerchantDetailComponent,
        MerchantUpdateComponent,
        MerchantDeleteDialogComponent,
        MerchantDeletePopupComponent
    ],
    entryComponents: [MerchantComponent, MerchantUpdateComponent, MerchantDeleteDialogComponent, MerchantDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Fpr360MerchantModule {}
