import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ClientCrm } from 'app/shared/model/client-crm.model';
import { ClientCrmService } from './client-crm.service';
import { ClientCrmComponent } from './client-crm.component';
import { ClientCrmDetailComponent } from './client-crm-detail.component';
import { ClientCrmUpdateComponent } from './client-crm-update.component';
import { ClientCrmDeletePopupComponent } from './client-crm-delete-dialog.component';
import { IClientCrm } from 'app/shared/model/client-crm.model';

@Injectable({ providedIn: 'root' })
export class ClientCrmResolve implements Resolve<IClientCrm> {
    constructor(private service: ClientCrmService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((clientCrm: HttpResponse<ClientCrm>) => clientCrm.body));
        }
        return of(new ClientCrm());
    }
}

export const clientCrmRoute: Routes = [
    {
        path: 'client-crm',
        component: ClientCrmComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'ClientCrms'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'client-crm/:id/view',
        component: ClientCrmDetailComponent,
        resolve: {
            clientCrm: ClientCrmResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ClientCrms'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'client-crm/new',
        component: ClientCrmUpdateComponent,
        resolve: {
            clientCrm: ClientCrmResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ClientCrms'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'client-crm/:id/edit',
        component: ClientCrmUpdateComponent,
        resolve: {
            clientCrm: ClientCrmResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ClientCrms'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clientCrmPopupRoute: Routes = [
    {
        path: 'client-crm/:id/delete',
        component: ClientCrmDeletePopupComponent,
        resolve: {
            clientCrm: ClientCrmResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ClientCrms'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
