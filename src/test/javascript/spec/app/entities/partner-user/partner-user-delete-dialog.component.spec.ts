/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Fpr360TestModule } from '../../../test.module';
import { PartnerUserDeleteDialogComponent } from 'app/entities/partner-user/partner-user-delete-dialog.component';
import { PartnerUserService } from 'app/entities/partner-user/partner-user.service';

describe('Component Tests', () => {
    describe('PartnerUser Management Delete Component', () => {
        let comp: PartnerUserDeleteDialogComponent;
        let fixture: ComponentFixture<PartnerUserDeleteDialogComponent>;
        let service: PartnerUserService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Fpr360TestModule],
                declarations: [PartnerUserDeleteDialogComponent]
            })
                .overrideTemplate(PartnerUserDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PartnerUserDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartnerUserService);
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
