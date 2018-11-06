import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ClientUser } from 'app/shared/model/client-user.model';
import { ClientUserService } from './client-user.service';
import { ClientUserComponent } from './client-user.component';
import { ClientUserDetailComponent } from './client-user-detail.component';
import { ClientUserUpdateComponent } from './client-user-update.component';
import { ClientUserDeletePopupComponent } from './client-user-delete-dialog.component';
import { IClientUser } from 'app/shared/model/client-user.model';

@Injectable({ providedIn: 'root' })
export class ClientUserResolve implements Resolve<IClientUser> {
    constructor(private service: ClientUserService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((clientUser: HttpResponse<ClientUser>) => clientUser.body));
        }
        return of(new ClientUser());
    }
}

export const clientUserRoute: Routes = [
    {
        path: 'client-user',
        component: ClientUserComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'ClientUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'client-user/:id/view',
        component: ClientUserDetailComponent,
        resolve: {
            clientUser: ClientUserResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ClientUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'client-user/new',
        component: ClientUserUpdateComponent,
        resolve: {
            clientUser: ClientUserResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ClientUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'client-user/:id/edit',
        component: ClientUserUpdateComponent,
        resolve: {
            clientUser: ClientUserResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ClientUsers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clientUserPopupRoute: Routes = [
    {
        path: 'client-user/:id/delete',
        component: ClientUserDeletePopupComponent,
        resolve: {
            clientUser: ClientUserResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ClientUsers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
