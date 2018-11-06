import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IMerchantAccount } from 'app/shared/model/merchant-account.model';
import { MerchantAccountService } from './merchant-account.service';
import { IMerchant } from 'app/shared/model/merchant.model';
import { MerchantService } from 'app/entities/merchant';
import { IClientCrm } from 'app/shared/model/client-crm.model';
import { ClientCrmService } from 'app/entities/client-crm';

@Component({
    selector: 'jhi-merchant-account-update',
    templateUrl: './merchant-account-update.component.html'
})
export class MerchantAccountUpdateComponent implements OnInit {
    private _merchantAccount: IMerchantAccount;
    isSaving: boolean;

    merchants: IMerchant[];

    clientcrms: IClientCrm[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private merchantAccountService: MerchantAccountService,
        private merchantService: MerchantService,
        private clientCrmService: ClientCrmService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ merchantAccount }) => {
            this.merchantAccount = merchantAccount;
        });
        this.merchantService.query().subscribe(
            (res: HttpResponse<IMerchant[]>) => {
                this.merchants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.clientCrmService.query().subscribe(
            (res: HttpResponse<IClientCrm[]>) => {
                this.clientcrms = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.merchantAccount.id !== undefined) {
            this.subscribeToSaveResponse(this.merchantAccountService.update(this.merchantAccount));
        } else {
            this.subscribeToSaveResponse(this.merchantAccountService.create(this.merchantAccount));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMerchantAccount>>) {
        result.subscribe((res: HttpResponse<IMerchantAccount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMerchantById(index: number, item: IMerchant) {
        return item.id;
    }

    trackClientCrmById(index: number, item: IClientCrm) {
        return item.id;
    }
    get merchantAccount() {
        return this._merchantAccount;
    }

    set merchantAccount(merchantAccount: IMerchantAccount) {
        this._merchantAccount = merchantAccount;
    }
}
