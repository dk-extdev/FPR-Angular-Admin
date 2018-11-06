import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IMerchant } from 'app/shared/model/merchant.model';
import { MerchantService } from './merchant.service';
import { IIndustry } from 'app/shared/model/industry.model';
import { IndustryService } from 'app/entities/industry';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client';

@Component({
    selector: 'jhi-merchant-update',
    templateUrl: './merchant-update.component.html'
})
export class MerchantUpdateComponent implements OnInit {
    private _merchant: IMerchant;
    isSaving: boolean;

    industries: IIndustry[];

    clients: IClient[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private merchantService: MerchantService,
        private industryService: IndustryService,
        private clientService: ClientService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ merchant }) => {
            this.merchant = merchant;
        });
        this.industryService.query().subscribe(
            (res: HttpResponse<IIndustry[]>) => {
                this.industries = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.clientService.query().subscribe(
            (res: HttpResponse<IClient[]>) => {
                this.clients = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.merchant.id !== undefined) {
            this.subscribeToSaveResponse(this.merchantService.update(this.merchant));
        } else {
            this.subscribeToSaveResponse(this.merchantService.create(this.merchant));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMerchant>>) {
        result.subscribe((res: HttpResponse<IMerchant>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackIndustryById(index: number, item: IIndustry) {
        return item.id;
    }

    trackClientById(index: number, item: IClient) {
        return item.id;
    }
    get merchant() {
        return this._merchant;
    }

    set merchant(merchant: IMerchant) {
        this._merchant = merchant;
    }
}
