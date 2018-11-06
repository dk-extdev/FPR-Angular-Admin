/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Fpr360TestModule } from '../../../test.module';
import { ClientCrmDeleteDialogComponent } from 'app/entities/client-crm/client-crm-delete-dialog.component';
import { ClientCrmService } from 'app/entities/client-crm/client-crm.service';

describe('Component Tests', () => {
    describe('ClientCrm Management Delete Component', () => {
        let comp: ClientCrmDeleteDialogComponent;
        let fixture: ComponentFixture<ClientCrmDeleteDialogComponent>;
        let service: ClientCrmService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Fpr360TestModule],
                declarations: [ClientCrmDeleteDialogComponent]
            })
                .overrideTemplate(ClientCrmDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClientCrmDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClientCrmService);
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
