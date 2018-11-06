import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { MerchantAccount } from 'app/shared/model/merchant-account.model';
import { MerchantAccountService } from './merchant-account.service';
import { MerchantAccountComponent } from './merchant-account.component';
import { MerchantAccountDetailComponent } from './merchant-account-detail.component';
import { MerchantAccountUpdateComponent } from './merchant-account-update.component';
import { MerchantAccountDeletePopupComponent } from './merchant-account-delete-dialog.component';
import { IMerchantAccount } from 'app/shared/model/merchant-account.model';

@Injectable({ providedIn: 'root' })
export class MerchantAccountResolve implements Resolve<IMerchantAccount> {
    constructor(private service: MerchantAccountService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((merchantAccount: HttpResponse<MerchantAccount>) => merchantAccount.body));
        }
        return of(new MerchantAccount());
    }
}

export const merchantAccountRoute: Routes = [
    {
        path: 'merchant-account',
        component: MerchantAccountComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'MerchantAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'merchant-account/:id/view',
        component: MerchantAccountDetailComponent,
        resolve: {
            merchantAccount: MerchantAccountResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'MerchantAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'merchant-account/new',
        component: MerchantAccountUpdateComponent,
        resolve: {
            merchantAccount: MerchantAccountResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'MerchantAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'merchant-account/:id/edit',
        component: MerchantAccountUpdateComponent,
        resolve: {
            merchantAccount: MerchantAccountResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'MerchantAccounts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const merchantAccountPopupRoute: Routes = [
    {
        path: 'merchant-account/:id/delete',
        component: MerchantAccountDeletePopupComponent,
        resolve: {
            merchantAccount: MerchantAccountResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'MerchantAccounts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
