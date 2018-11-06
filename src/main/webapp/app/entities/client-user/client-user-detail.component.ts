import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClientUser } from 'app/shared/model/client-user.model';

@Component({
    selector: 'jhi-client-user-detail',
    templateUrl: './client-user-detail.component.html'
})
export class ClientUserDetailComponent implements OnInit {
    clientUser: IClientUser;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ clientUser }) => {
            this.clientUser = clientUser;
        });
    }

    previousState() {
        window.history.back();
    }
}
