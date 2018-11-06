import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Industry } from 'app/shared/model/industry.model';
import { IndustryService } from './industry.service';
import { IndustryComponent } from './industry.component';
import { IndustryDetailComponent } from './industry-detail.component';
import { IndustryUpdateComponent } from './industry-update.component';
import { IndustryDeletePopupComponent } from './industry-delete-dialog.component';
import { IIndustry } from 'app/shared/model/industry.model';

@Injectable({ providedIn: 'root' })
export class IndustryResolve implements Resolve<IIndustry> {
    constructor(private service: IndustryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((industry: HttpResponse<Industry>) => industry.body));
        }
        return of(new Industry());
    }
}

export const industryRoute: Routes = [
    {
        path: 'industry',
        component: IndustryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'Industries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'industry/:id/view',
        component: IndustryDetailComponent,
        resolve: {
            industry: IndustryResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Industries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'industry/new',
        component: IndustryUpdateComponent,
        resolve: {
            industry: IndustryResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Industries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'industry/:id/edit',
        component: IndustryUpdateComponent,
        resolve: {
            industry: IndustryResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Industries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const industryPopupRoute: Routes = [
    {
        path: 'industry/:id/delete',
        component: IndustryDeletePopupComponent,
        resolve: {
            industry: IndustryResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Industries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
