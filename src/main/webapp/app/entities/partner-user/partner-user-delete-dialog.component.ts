import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartnerUser } from 'app/shared/model/partner-user.model';
import { PartnerUserService } from './partner-user.service';

@Component({
    selector: 'jhi-partner-user-delete-dialog',
    templateUrl: './partner-user-delete-dialog.component.html'
})
export class PartnerUserDeleteDialogComponent {
    partnerUser: IPartnerUser;

    constructor(
        private partnerUserService: PartnerUserService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.partnerUserService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'partnerUserListModification',
                content: 'Deleted an partnerUser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-partner-user-delete-popup',
    template: ''
})
export class PartnerUserDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ partnerUser }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PartnerUserDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.partnerUser = partnerUser;
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
