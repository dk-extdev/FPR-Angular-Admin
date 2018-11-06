import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IClientUser } from 'app/shared/model/client-user.model';
import { ClientUserService } from './client-user.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-client-user-update',
    templateUrl: './client-user-update.component.html'
})
export class ClientUserUpdateComponent implements OnInit {
    private _clientUser: IClientUser;
    isSaving: boolean;

    clients: IClient[];

    users: IUser[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private clientUserService: ClientUserService,
        private clientService: ClientService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ clientUser }) => {
            this.clientUser = clientUser;
        });
        this.clientService.query().subscribe(
            (res: HttpResponse<IClient[]>) => {
                this.clients = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.clientUser.id !== undefined) {
            this.subscribeToSaveResponse(this.clientUserService.update(this.clientUser));
        } else {
            this.subscribeToSaveResponse(this.clientUserService.create(this.clientUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IClientUser>>) {
        result.subscribe((res: HttpResponse<IClientUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackClientById(index: number, item: IClient) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
    get clientUser() {
        return this._clientUser;
    }

    set clientUser(clientUser: IClientUser) {
        this._clientUser = clientUser;
    }
}
