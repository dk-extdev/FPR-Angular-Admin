import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGuruClient } from 'app/shared/model/guru-client.model';
import { GuruClientService } from './guru-client.service';

@Component({
    selector: 'jhi-guru-client-delete-dialog',
    templateUrl: './guru-client-delete-dialog.component.html'
})
export class GuruClientDeleteDialogComponent {
    guruClient: IGuruClient;

    constructor(private guruClientService: GuruClientService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.guruClientService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'guruClientListModification',
                content: 'Deleted an guruClient'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-guru-client-delete-popup',
    template: ''
})
export class GuruClientDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ guruClient }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GuruClientDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.guruClient = guruClient;
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
