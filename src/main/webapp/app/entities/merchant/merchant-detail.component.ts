import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMerchant } from 'app/shared/model/merchant.model';

@Component({
    selector: 'jhi-merchant-detail',
    templateUrl: './merchant-detail.component.html'
})
export class MerchantDetailComponent implements OnInit {
    merchant: IMerchant;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ merchant }) => {
            this.merchant = merchant;
        });
    }

    previousState() {
        window.history.back();
    }
}
