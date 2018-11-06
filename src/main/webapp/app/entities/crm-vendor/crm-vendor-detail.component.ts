import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICrmVendor } from 'app/shared/model/crm-vendor.model';

@Component({
    selector: 'jhi-crm-vendor-detail',
    templateUrl: './crm-vendor-detail.component.html'
})
export class CrmVendorDetailComponent implements OnInit {
    crmVendor: ICrmVendor;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ crmVendor }) => {
            this.crmVendor = crmVendor;
        });
    }

    previousState() {
        window.history.back();
    }
}
