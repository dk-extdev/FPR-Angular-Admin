import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ContactInfo } from 'app/shared/model/contact-info.model';
import { ContactInfoService } from './contact-info.service';
import { ContactInfoComponent } from './contact-info.component';
import { ContactInfoDetailComponent } from './contact-info-detail.component';
import { ContactInfoUpdateComponent } from './contact-info-update.component';
import { ContactInfoDeletePopupComponent } from './contact-info-delete-dialog.component';
import { IContactInfo } from 'app/shared/model/contact-info.model';

@Injectable({ providedIn: 'root' })
export class ContactInfoResolve implements Resolve<IContactInfo> {
    constructor(private service: ContactInfoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((contactInfo: HttpResponse<ContactInfo>) => contactInfo.body));
        }
        return of(new ContactInfo());
    }
}

export const contactInfoRoute: Routes = [
    {
        path: 'contact-info',
        component: ContactInfoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'ContactInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contact-info/:id/view',
        component: ContactInfoDetailComponent,
        resolve: {
            contactInfo: ContactInfoResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ContactInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contact-info/new',
        component: ContactInfoUpdateComponent,
        resolve: {
            contactInfo: ContactInfoResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ContactInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contact-info/:id/edit',
        component: ContactInfoUpdateComponent,
        resolve: {
            contactInfo: ContactInfoResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ContactInfos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contactInfoPopupRoute: Routes = [
    {
        path: 'contact-info/:id/delete',
        component: ContactInfoDeletePopupComponent,
        resolve: {
            contactInfo: ContactInfoResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ContactInfos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
