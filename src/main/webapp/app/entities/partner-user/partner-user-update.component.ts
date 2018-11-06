import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPartnerUser } from 'app/shared/model/partner-user.model';
import { PartnerUserService } from './partner-user.service';
import { IPartner } from 'app/shared/model/partner.model';
import { PartnerService } from 'app/entities/partner';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-partner-user-update',
    templateUrl: './partner-user-update.component.html'
})
export class PartnerUserUpdateComponent implements OnInit {
    private _partnerUser: IPartnerUser;
    isSaving: boolean;

    partners: IPartner[];

    users: IUser[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private partnerUserService: PartnerUserService,
        private partnerService: PartnerService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ partnerUser }) => {
            this.partnerUser = partnerUser;
        });
        this.partnerService.query().subscribe(
            (res: HttpResponse<IPartner[]>) => {
                this.partners = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.partnerUser.id !== undefined) {
            this.subscribeToSaveResponse(this.partnerUserService.update(this.partnerUser));
        } else {
            this.subscribeToSaveResponse(this.partnerUserService.create(this.partnerUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPartnerUser>>) {
        result.subscribe((res: HttpResponse<IPartnerUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPartnerById(index: number, item: IPartner) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
    get partnerUser() {
        return this._partnerUser;
    }

    set partnerUser(partnerUser: IPartnerUser) {
        this._partnerUser = partnerUser;
    }
}
