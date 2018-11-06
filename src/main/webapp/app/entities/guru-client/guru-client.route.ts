import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { GuruClient } from 'app/shared/model/guru-client.model';
import { GuruClientService } from './guru-client.service';
import { GuruClientComponent } from './guru-client.component';
import { GuruClientDetailComponent } from './guru-client-detail.component';
import { GuruClientUpdateComponent } from './guru-client-update.component';
import { GuruClientDeletePopupComponent } from './guru-client-delete-dialog.component';
import { IGuruClient } from 'app/shared/model/guru-client.model';

@Injectable({ providedIn: 'root' })
export class GuruClientResolve implements Resolve<IGuruClient> {
    constructor(private service: GuruClientService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((guruClient: HttpResponse<GuruClient>) => guruClient.body));
        }
        return of(new GuruClient());
    }
}

export const guruClientRoute: Routes = [
    {
        path: 'guru-client',
        component: GuruClientComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'GuruClients'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'guru-client/:id/view',
        component: GuruClientDetailComponent,
        resolve: {
            guruClient: GuruClientResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'GuruClients'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'guru-client/new',
        component: GuruClientUpdateComponent,
        resolve: {
            guruClient: GuruClientResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'GuruClients'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'guru-client/:id/edit',
        component: GuruClientUpdateComponent,
        resolve: {
            guruClient: GuruClientResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'GuruClients'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const guruClientPopupRoute: Routes = [
    {
        path: 'guru-client/:id/delete',
        component: GuruClientDeletePopupComponent,
        resolve: {
            guruClient: GuruClientResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'GuruClients'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
