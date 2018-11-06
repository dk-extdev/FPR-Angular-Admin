import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IGuruClient } from 'app/shared/model/guru-client.model';
import { GuruClientService } from './guru-client.service';
import { IUser, UserService } from 'app/core';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client';

@Component({
    selector: 'jhi-guru-client-update',
    templateUrl: './guru-client-update.component.html'
})
export class GuruClientUpdateComponent implements OnInit {
    private _guruClient: IGuruClient;
    isSaving: boolean;

    users: IUser[];

    clients: IClient[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private guruClientService: GuruClientService,
        private userService: UserService,
        private clientService: ClientService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ guruClient }) => {
            this.guruClient = guruClient;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.clientService.query().subscribe(
            (res: HttpResponse<IClient[]>) => {
                this.clients = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.guruClient.id !== undefined) {
            this.subscribeToSaveResponse(this.guruClientService.update(this.guruClient));
        } else {
            this.subscribeToSaveResponse(this.guruClientService.create(this.guruClient));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGuruClient>>) {
        result.subscribe((res: HttpResponse<IGuruClient>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackClientById(index: number, item: IClient) {
        return item.id;
    }
    get guruClient() {
        return this._guruClient;
    }

    set guruClient(guruClient: IGuruClient) {
        this._guruClient = guruClient;
    }
}
