import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICrmVendor } from 'app/shared/model/crm-vendor.model';
import { CrmVendorService } from './crm-vendor.service';

@Component({
    selector: 'jhi-crm-vendor-delete-dialog',
    templateUrl: './crm-vendor-delete-dialog.component.html'
})
export class CrmVendorDeleteDialogComponent {
    crmVendor: ICrmVendor;

    constructor(private crmVendorService: CrmVendorService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.crmVendorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'crmVendorListModification',
                content: 'Deleted an crmVendor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-crm-vendor-delete-popup',
    template: ''
})
export class CrmVendorDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ crmVendor }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CrmVendorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.crmVendor = crmVendor;
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
