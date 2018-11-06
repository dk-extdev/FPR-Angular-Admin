import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PartnerClient } from 'app/shared/model/partner-client.model';
import { PartnerClientService } from './partner-client.service';
import { PartnerClientComponent } from './partner-client.component';
import { PartnerClientDetailComponent } from './partner-client-detail.component';
import { PartnerClientUpdateComponent } from './partner-client-update.component';
import { PartnerClientDeletePopupComponent } from './partner-client-delete-dialog.component';
import { IPartnerClient } from 'app/shared/model/partner-client.model';

@Injectable({ providedIn: 'root' })
export class PartnerClientResolve implements Resolve<IPartnerClient> {
    constructor(private service: PartnerClientService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((partnerClient: HttpResponse<PartnerClient>) => partnerClient.body));
        }
        return of(new PartnerClient());
    }
}

export const partnerClientRoute: Routes = [
    {
        path: 'partner-client',
        component: PartnerClientComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'PartnerClients'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'partner-client/:id/view',
        component: PartnerClientDetailComponent,
        resolve: {
            partnerClient: PartnerClientResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'PartnerClients'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'partner-client/new',
        component: PartnerClientUpdateComponent,
        resolve: {
            partnerClient: PartnerClientResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'PartnerClients'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'partner-client/:id/edit',
        component: PartnerClientUpdateComponent,
        resolve: {
            partnerClient: PartnerClientResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'PartnerClients'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const partnerClientPopupRoute: Routes = [
    {
        path: 'partner-client/:id/delete',
        component: PartnerClientDeletePopupComponent,
        resolve: {
            partnerClient: PartnerClientResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'PartnerClients'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
