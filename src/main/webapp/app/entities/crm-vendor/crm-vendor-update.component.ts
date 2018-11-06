import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICrmVendor } from 'app/shared/model/crm-vendor.model';
import { CrmVendorService } from './crm-vendor.service';

@Component({
    selector: 'jhi-crm-vendor-update',
    templateUrl: './crm-vendor-update.component.html'
})
export class CrmVendorUpdateComponent implements OnInit {
    private _crmVendor: ICrmVendor;
    isSaving: boolean;

    constructor(private crmVendorService: CrmVendorService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ crmVendor }) => {
            this.crmVendor = crmVendor;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.crmVendor.id !== undefined) {
            this.subscribeToSaveResponse(this.crmVendorService.update(this.crmVendor));
        } else {
            this.subscribeToSaveResponse(this.crmVendorService.create(this.crmVendor));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICrmVendor>>) {
        result.subscribe((res: HttpResponse<ICrmVendor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get crmVendor() {
        return this._crmVendor;
    }

    set crmVendor(crmVendor: ICrmVendor) {
        this._crmVendor = crmVendor;
    }
}
