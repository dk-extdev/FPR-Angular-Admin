import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartnerClient } from 'app/shared/model/partner-client.model';
import { PartnerClientService } from './partner-client.service';

@Component({
    selector: 'jhi-partner-client-delete-dialog',
    templateUrl: './partner-client-delete-dialog.component.html'
})
export class PartnerClientDeleteDialogComponent {
    partnerClient: IPartnerClient;

    constructor(
        private partnerClientService: PartnerClientService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.partnerClientService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'partnerClientListModification',
                content: 'Deleted an partnerClient'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-partner-client-delete-popup',
    template: ''
})
export class PartnerClientDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ partnerClient }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PartnerClientDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.partnerClient = partnerClient;
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
