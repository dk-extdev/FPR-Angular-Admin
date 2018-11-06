import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMerchantAccount } from 'app/shared/model/merchant-account.model';
import { MerchantAccountService } from './merchant-account.service';

@Component({
    selector: 'jhi-merchant-account-delete-dialog',
    templateUrl: './merchant-account-delete-dialog.component.html'
})
export class MerchantAccountDeleteDialogComponent {
    merchantAccount: IMerchantAccount;

    constructor(
        private merchantAccountService: MerchantAccountService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.merchantAccountService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'merchantAccountListModification',
                content: 'Deleted an merchantAccount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-merchant-account-delete-popup',
    template: ''
})
export class MerchantAccountDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ merchantAccount }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MerchantAccountDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.merchantAccount = merchantAccount;
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
