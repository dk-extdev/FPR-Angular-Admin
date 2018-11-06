import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClientCrm } from 'app/shared/model/client-crm.model';

@Component({
    selector: 'jhi-client-crm-detail',
    templateUrl: './client-crm-detail.component.html'
})
export class ClientCrmDetailComponent implements OnInit {
    clientCrm: IClientCrm;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ clientCrm }) => {
            this.clientCrm = clientCrm;
        });
    }

    previousState() {
        window.history.back();
    }
}
