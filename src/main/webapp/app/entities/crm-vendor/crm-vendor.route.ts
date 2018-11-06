import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CrmVendor } from 'app/shared/model/crm-vendor.model';
import { CrmVendorService } from './crm-vendor.service';
import { CrmVendorComponent } from './crm-vendor.component';
import { CrmVendorDetailComponent } from './crm-vendor-detail.component';
import { CrmVendorUpdateComponent } from './crm-vendor-update.component';
import { CrmVendorDeletePopupComponent } from './crm-vendor-delete-dialog.component';
import { ICrmVendor } from 'app/shared/model/crm-vendor.model';

@Injectable({ providedIn: 'root' })
export class CrmVendorResolve implements Resolve<ICrmVendor> {
    constructor(private service: CrmVendorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((crmVendor: HttpResponse<CrmVendor>) => crmVendor.body));
        }
        return of(new CrmVendor());
    }
}

export const crmVendorRoute: Routes = [
    {
        path: 'crm-vendor',
        component: CrmVendorComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'CrmVendors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'crm-vendor/:id/view',
        component: CrmVendorDetailComponent,
        resolve: {
            crmVendor: CrmVendorResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'CrmVendors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'crm-vendor/new',
        component: CrmVendorUpdateComponent,
        resolve: {
            crmVendor: CrmVendorResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'CrmVendors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'crm-vendor/:id/edit',
        component: CrmVendorUpdateComponent,
        resolve: {
            crmVendor: CrmVendorResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'CrmVendors'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const crmVendorPopupRoute: Routes = [
    {
        path: 'crm-vendor/:id/delete',
        component: CrmVendorDeletePopupComponent,
        resolve: {
            crmVendor: CrmVendorResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'CrmVendors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
