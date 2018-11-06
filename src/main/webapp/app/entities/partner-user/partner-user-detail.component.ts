import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartnerUser } from 'app/shared/model/partner-user.model';

@Component({
    selector: 'jhi-partner-user-detail',
    templateUrl: './partner-user-detail.component.html'
})
export class PartnerUserDetailComponent implements OnInit {
    partnerUser: IPartnerUser;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ partnerUser }) => {
            this.partnerUser = partnerUser;
        });
    }

    previousState() {
        window.history.back();
    }
}
