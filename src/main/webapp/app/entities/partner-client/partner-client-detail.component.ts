import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartnerClient } from 'app/shared/model/partner-client.model';

@Component({
    selector: 'jhi-partner-client-detail',
    templateUrl: './partner-client-detail.component.html'
})
export class PartnerClientDetailComponent implements OnInit {
    partnerClient: IPartnerClient;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ partnerClient }) => {
            this.partnerClient = partnerClient;
        });
    }

    previousState() {
        window.history.back();
    }
}
