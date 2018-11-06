import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IClientCrm } from 'app/shared/model/client-crm.model';
import { ClientCrmService } from './client-crm.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client';
import { ICrmVendor } from 'app/shared/model/crm-vendor.model';
import { CrmVendorService } from 'app/entities/crm-vendor';

@Component({
    selector: 'jhi-client-crm-update',
    templateUrl: './client-crm-update.component.html'
})
export class ClientCrmUpdateComponent implements OnInit {
    private _clientCrm: IClientCrm;
    isSaving: boolean;

    clients: IClient[];

    crmvendors: ICrmVendor[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private clientCrmService: ClientCrmService,
        private clientService: ClientService,
        private crmVendorService: CrmVendorService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ clientCrm }) => {
            this.clientCrm = clientCrm;
        });
        this.clientService.query().subscribe(
            (res: HttpResponse<IClient[]>) => {
                this.clients = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.crmVendorService.query().subscribe(
            (res: HttpResponse<ICrmVendor[]>) => {
                this.crmvendors = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.clientCrm.id !== undefined) {
            this.subscribeToSaveResponse(this.clientCrmService.update(this.clientCrm));
        } else {
            this.subscribeToSaveResponse(this.clientCrmService.create(this.clientCrm));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IClientCrm>>) {
        result.subscribe((res: HttpResponse<IClientCrm>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackClientById(index: number, item: IClient) {
        return item.id;
    }

    trackCrmVendorById(index: number, item: ICrmVendor) {
        return item.id;
    }
    get clientCrm() {
        return this._clientCrm;
    }

    set clientCrm(clientCrm: IClientCrm) {
        this._clientCrm = clientCrm;
    }
}
