import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Fpr360SharedModule } from 'app/shared';
import { Fpr360AdminModule } from 'app/admin/admin.module';
import {
    PartnerUserComponent,
    PartnerUserDetailComponent,
    PartnerUserUpdateComponent,
    PartnerUserDeletePopupComponent,
    PartnerUserDeleteDialogComponent,
    partnerUserRoute,
    partnerUserPopupRoute
} from './';

const ENTITY_STATES = [...partnerUserRoute, ...partnerUserPopupRoute];

@NgModule({
    imports: [Fpr360SharedModule, Fpr360AdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PartnerUserComponent,
        PartnerUserDetailComponent,
        PartnerUserUpdateComponent,
        PartnerUserDeleteDialogComponent,
        PartnerUserDeletePopupComponent
    ],
    entryComponents: [PartnerUserComponent, PartnerUserUpdateComponent, PartnerUserDeleteDialogComponent, PartnerUserDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Fpr360PartnerUserModule {}
