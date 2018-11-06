import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Fpr360SharedModule } from 'app/shared';
import {
    PartnerClientComponent,
    PartnerClientDetailComponent,
    PartnerClientUpdateComponent,
    PartnerClientDeletePopupComponent,
    PartnerClientDeleteDialogComponent,
    partnerClientRoute,
    partnerClientPopupRoute
} from './';

const ENTITY_STATES = [...partnerClientRoute, ...partnerClientPopupRoute];

@NgModule({
    imports: [Fpr360SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PartnerClientComponent,
        PartnerClientDetailComponent,
        PartnerClientUpdateComponent,
        PartnerClientDeleteDialogComponent,
        PartnerClientDeletePopupComponent
    ],
    entryComponents: [
        PartnerClientComponent,
        PartnerClientUpdateComponent,
        PartnerClientDeleteDialogComponent,
        PartnerClientDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Fpr360PartnerClientModule {}
