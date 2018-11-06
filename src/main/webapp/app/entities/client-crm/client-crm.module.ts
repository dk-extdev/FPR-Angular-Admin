import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Fpr360SharedModule } from 'app/shared';
import {
    ClientCrmComponent,
    ClientCrmDetailComponent,
    ClientCrmUpdateComponent,
    ClientCrmDeletePopupComponent,
    ClientCrmDeleteDialogComponent,
    clientCrmRoute,
    clientCrmPopupRoute
} from './';

const ENTITY_STATES = [...clientCrmRoute, ...clientCrmPopupRoute];

@NgModule({
    imports: [Fpr360SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ClientCrmComponent,
        ClientCrmDetailComponent,
        ClientCrmUpdateComponent,
        ClientCrmDeleteDialogComponent,
        ClientCrmDeletePopupComponent
    ],
    entryComponents: [ClientCrmComponent, ClientCrmUpdateComponent, ClientCrmDeleteDialogComponent, ClientCrmDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Fpr360ClientCrmModule {}
