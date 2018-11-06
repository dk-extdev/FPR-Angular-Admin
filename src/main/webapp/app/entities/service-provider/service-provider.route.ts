import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ServiceProvider } from 'app/shared/model/service-provider.model';
import { ServiceProviderService } from './service-provider.service';
import { ServiceProviderComponent } from './service-provider.component';
import { ServiceProviderDetailComponent } from './service-provider-detail.component';
import { ServiceProviderUpdateComponent } from './service-provider-update.component';
import { ServiceProviderDeletePopupComponent } from './service-provider-delete-dialog.component';
import { IServiceProvider } from 'app/shared/model/service-provider.model';

@Injectable({ providedIn: 'root' })
export class ServiceProviderResolve implements Resolve<IServiceProvider> {
    constructor(private service: ServiceProviderService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((serviceProvider: HttpResponse<ServiceProvider>) => serviceProvider.body));
        }
        return of(new ServiceProvider());
    }
}

export const serviceProviderRoute: Routes = [
    {
        path: 'service-provider',
        component: ServiceProviderComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'ServiceProviders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'service-provider/:id/view',
        component: ServiceProviderDetailComponent,
        resolve: {
            serviceProvider: ServiceProviderResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ServiceProviders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'service-provider/new',
        component: ServiceProviderUpdateComponent,
        resolve: {
            serviceProvider: ServiceProviderResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ServiceProviders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'service-provider/:id/edit',
        component: ServiceProviderUpdateComponent,
        resolve: {
            serviceProvider: ServiceProviderResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ServiceProviders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const serviceProviderPopupRoute: Routes = [
    {
        path: 'service-provider/:id/delete',
        component: ServiceProviderDeletePopupComponent,
        resolve: {
            serviceProvider: ServiceProviderResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ServiceProviders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
