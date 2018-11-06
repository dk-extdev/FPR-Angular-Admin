import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Fpr360SharedModule } from 'app/shared';
import { Fpr360AdminModule } from 'app/admin/admin.module';
import {
    ClientUserComponent,
    ClientUserDetailComponent,
    ClientUserUpdateComponent,
    ClientUserDeletePopupComponent,
    ClientUserDeleteDialogComponent,
    clientUserRoute,
    clientUserPopupRoute
} from './';

const ENTITY_STATES = [...clientUserRoute, ...clientUserPopupRoute];

@NgModule({
    imports: [Fpr360SharedModule, Fpr360AdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ClientUserComponent,
        ClientUserDetailComponent,
        ClientUserUpdateComponent,
        ClientUserDeleteDialogComponent,
        ClientUserDeletePopupComponent
    ],
    entryComponents: [ClientUserComponent, ClientUserUpdateComponent, ClientUserDeleteDialogComponent, ClientUserDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Fpr360ClientUserModule {}
