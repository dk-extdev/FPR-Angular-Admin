import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMerchantAccount } from 'app/shared/model/merchant-account.model';

@Component({
    selector: 'jhi-merchant-account-detail',
    templateUrl: './merchant-account-detail.component.html'
})
export class MerchantAccountDetailComponent implements OnInit {
    merchantAccount: IMerchantAccount;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ merchantAccount }) => {
            this.merchantAccount = merchantAccount;
        });
    }

    previousState() {
        window.history.back();
    }
}
