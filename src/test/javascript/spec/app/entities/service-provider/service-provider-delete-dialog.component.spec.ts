/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Fpr360TestModule } from '../../../test.module';
import { ServiceProviderDeleteDialogComponent } from 'app/entities/service-provider/service-provider-delete-dialog.component';
import { ServiceProviderService } from 'app/entities/service-provider/service-provider.service';

describe('Component Tests', () => {
    describe('ServiceProvider Management Delete Component', () => {
        let comp: ServiceProviderDeleteDialogComponent;
        let fixture: ComponentFixture<ServiceProviderDeleteDialogComponent>;
        let service: ServiceProviderService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Fpr360TestModule],
                declarations: [ServiceProviderDeleteDialogComponent]
            })
                .overrideTemplate(ServiceProviderDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ServiceProviderDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServiceProviderService);
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
