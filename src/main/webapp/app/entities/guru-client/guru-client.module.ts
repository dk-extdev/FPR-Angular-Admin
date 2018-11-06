import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Fpr360SharedModule } from 'app/shared';
import { Fpr360AdminModule } from 'app/admin/admin.module';
import {
    GuruClientComponent,
    GuruClientDetailComponent,
    GuruClientUpdateComponent,
    GuruClientDeletePopupComponent,
    GuruClientDeleteDialogComponent,
    guruClientRoute,
    guruClientPopupRoute
} from './';

const ENTITY_STATES = [...guruClientRoute, ...guruClientPopupRoute];

@NgModule({
    imports: [Fpr360SharedModule, Fpr360AdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GuruClientComponent,
        GuruClientDetailComponent,
        GuruClientUpdateComponent,
        GuruClientDeleteDialogComponent,
        GuruClientDeletePopupComponent
    ],
    entryComponents: [GuruClientComponent, GuruClientUpdateComponent, GuruClientDeleteDialogComponent, GuruClientDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Fpr360GuruClientModule {}
