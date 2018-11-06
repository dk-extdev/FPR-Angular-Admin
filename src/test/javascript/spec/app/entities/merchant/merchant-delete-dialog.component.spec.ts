/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Fpr360TestModule } from '../../../test.module';
import { MerchantDeleteDialogComponent } from 'app/entities/merchant/merchant-delete-dialog.component';
import { MerchantService } from 'app/entities/merchant/merchant.service';

describe('Component Tests', () => {
    describe('Merchant Management Delete Component', () => {
        let comp: MerchantDeleteDialogComponent;
        let fixture: ComponentFixture<MerchantDeleteDialogComponent>;
        let service: MerchantService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Fpr360TestModule],
                declarations: [MerchantDeleteDialogComponent]
            })
                .overrideTemplate(MerchantDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MerchantDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MerchantService);
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
