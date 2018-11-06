import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPartnerClient } from 'app/shared/model/partner-client.model';
import { PartnerClientService } from './partner-client.service';
import { IPartner } from 'app/shared/model/partner.model';
import { PartnerService } from 'app/entities/partner';
import { IMerchantAccount } from 'app/shared/model/merchant-account.model';
import { MerchantAccountService } from 'app/entities/merchant-account';

@Component({
    selector: 'jhi-partner-client-update',
    templateUrl: './partner-client-update.component.html'
})
export class PartnerClientUpdateComponent implements OnInit {
    private _partnerClient: IPartnerClient;
    isSaving: boolean;

    partners: IPartner[];

    merchantaccounts: IMerchantAccount[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private partnerClientService: PartnerClientService,
        private partnerService: PartnerService,
        private merchantAccountService: MerchantAccountService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ partnerClient }) => {
            this.partnerClient = partnerClient;
        });
        this.partnerService.query().subscribe(
            (res: HttpResponse<IPartner[]>) => {
                this.partners = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.merchantAccountService.query().subscribe(
            (res: HttpResponse<IMerchantAccount[]>) => {
                this.merchantaccounts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.partnerClient.id !== undefined) {
            this.subscribeToSaveResponse(this.partnerClientService.update(this.partnerClient));
        } else {
            this.subscribeToSaveResponse(this.partnerClientService.create(this.partnerClient));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPartnerClient>>) {
        result.subscribe((res: HttpResponse<IPartnerClient>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMerchantAccountById(index: number, item: IMerchantAccount) {
        return item.id;
    }
    get partnerClient() {
        return this._partnerClient;
    }

    set partnerClient(partnerClient: IPartnerClient) {
        this._partnerClient = partnerClient;
    }
}
