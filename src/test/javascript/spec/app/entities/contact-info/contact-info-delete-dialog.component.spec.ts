/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Fpr360TestModule } from '../../../test.module';
import { ContactInfoDeleteDialogComponent } from 'app/entities/contact-info/contact-info-delete-dialog.component';
import { ContactInfoService } from 'app/entities/contact-info/contact-info.service';

describe('Component Tests', () => {
    describe('ContactInfo Management Delete Component', () => {
        let comp: ContactInfoDeleteDialogComponent;
        let fixture: ComponentFixture<ContactInfoDeleteDialogComponent>;
        let service: ContactInfoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Fpr360TestModule],
                declarations: [ContactInfoDeleteDialogComponent]
            })
                .overrideTemplate(ContactInfoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContactInfoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactInfoService);
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
