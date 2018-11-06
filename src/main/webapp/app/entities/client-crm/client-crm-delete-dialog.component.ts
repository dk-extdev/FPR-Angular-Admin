import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClientCrm } from 'app/shared/model/client-crm.model';
import { ClientCrmService } from './client-crm.service';

@Component({
    selector: 'jhi-client-crm-delete-dialog',
    templateUrl: './client-crm-delete-dialog.component.html'
})
export class ClientCrmDeleteDialogComponent {
    clientCrm: IClientCrm;

    constructor(private clientCrmService: ClientCrmService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clientCrmService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'clientCrmListModification',
                content: 'Deleted an clientCrm'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-client-crm-delete-popup',
    template: ''
})
export class ClientCrmDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ clientCrm }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ClientCrmDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.clientCrm = clientCrm;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
