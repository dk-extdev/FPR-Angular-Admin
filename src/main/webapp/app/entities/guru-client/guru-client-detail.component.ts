import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGuruClient } from 'app/shared/model/guru-client.model';

@Component({
    selector: 'jhi-guru-client-detail',
    templateUrl: './guru-client-detail.component.html'
})
export class GuruClientDetailComponent implements OnInit {
    guruClient: IGuruClient;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ guruClient }) => {
            this.guruClient = guruClient;
        });
    }

    previousState() {
        window.history.back();
    }
}
