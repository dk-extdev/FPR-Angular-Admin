/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Fpr360TestModule } from '../../../test.module';
import { MerchantAccountDeleteDialogComponent } from 'app/entities/merchant-account/merchant-account-delete-dialog.component';
import { MerchantAccountService } from 'app/entities/merchant-account/merchant-account.service';

describe('Component Tests', () => {
    describe('MerchantAccount Management Delete Component', () => {
        let comp: MerchantAccountDeleteDialogComponent;
        let fixture: ComponentFixture<MerchantAccountDeleteDialogComponent>;
        let service: MerchantAccountService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Fpr360TestModule],
                declarations: [MerchantAccountDeleteDialogComponent]
            })
                .overrideTemplate(MerchantAccountDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MerchantAccountDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MerchantAccountService);
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
