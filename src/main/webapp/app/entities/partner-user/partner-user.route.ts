import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PartnerUser } from 'app/shared/model/partner-user.model';
import { PartnerUserService } from './partner-user.service';
import { PartnerUserComponent } from './partner-user.component';
import { PartnerUserDetailComponent } from './partner-user-detail.component';
import { PartnerUserUpdateComponent } from './partner-user-update.component';
import { PartnerUserDeletePopupComponent } from './partner-user-delete-dialog.component';
import { IPartnerUser } from 'app/shared/model/partner-user.model';

@Injectable({ providedIn: 'root' })
export class PartnerUserResolve implements Resolve<IPartnerUser> {
    constructor(private service: PartnerUserService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((partnerUser: HttpResponse<PartnerUser>) => partnerUser.body));
        }
        return of(new PartnerUser());
    }
}

export const partnerUserRoute: Routes = [
    {
        path: 'partner-user',
        component: PartnerUserComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'PartnerUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'partner-user/:id/view',
        component: PartnerUserDetailComponent,
        resolve: {
            partnerUser: PartnerUserResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'PartnerUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'partner-user/new',
        component: PartnerUserUpdateComponent,
        resolve: {
            partnerUser: PartnerUserResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'PartnerUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'partner-user/:id/edit',
        component: PartnerUserUpdateComponent,
        resolve: {
            partnerUser: PartnerUserResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'PartnerUsers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const partnerUserPopupRoute: Routes = [
    {
        path: 'partner-user/:id/delete',
        component: PartnerUserDeletePopupComponent,
        resolve: {
            partnerUser: PartnerUserResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'PartnerUsers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
