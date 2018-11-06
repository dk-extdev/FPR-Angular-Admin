import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Merchant } from 'app/shared/model/merchant.model';
import { MerchantService } from './merchant.service';
import { MerchantComponent } from './merchant.component';
import { MerchantDetailComponent } from './merchant-detail.component';
import { MerchantUpdateComponent } from './merchant-update.component';
import { MerchantDeletePopupComponent } from './merchant-delete-dialog.component';
import { IMerchant } from 'app/shared/model/merchant.model';

@Injectable({ providedIn: 'root' })
export class MerchantResolve implements Resolve<IMerchant> {
    constructor(private service: MerchantService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((merchant: HttpResponse<Merchant>) => merchant.body));
        }
        return of(new Merchant());
    }
}

export const merchantRoute: Routes = [
    {
        path: 'merchant',
        component: MerchantComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'Merchants'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'merchant/:id/view',
        component: MerchantDetailComponent,
        resolve: {
            merchant: MerchantResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Merchants'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'merchant/new',
        component: MerchantUpdateComponent,
        resolve: {
            merchant: MerchantResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Merchants'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'merchant/:id/edit',
        component: MerchantUpdateComponent,
        resolve: {
            merchant: MerchantResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Merchants'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const merchantPopupRoute: Routes = [
    {
        path: 'merchant/:id/delete',
        component: MerchantDeletePopupComponent,
        resolve: {
            merchant: MerchantResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Merchants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
