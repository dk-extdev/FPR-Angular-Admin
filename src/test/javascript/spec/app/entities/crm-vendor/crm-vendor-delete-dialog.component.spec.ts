/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Fpr360TestModule } from '../../../test.module';
import { CrmVendorDeleteDialogComponent } from 'app/entities/crm-vendor/crm-vendor-delete-dialog.component';
import { CrmVendorService } from 'app/entities/crm-vendor/crm-vendor.service';

describe('Component Tests', () => {
    describe('CrmVendor Management Delete Component', () => {
        let comp: CrmVendorDeleteDialogComponent;
        let fixture: ComponentFixture<CrmVendorDeleteDialogComponent>;
        let service: CrmVendorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Fpr360TestModule],
                declarations: [CrmVendorDeleteDialogComponent]
            })
                .overrideTemplate(CrmVendorDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CrmVendorDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CrmVendorService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
