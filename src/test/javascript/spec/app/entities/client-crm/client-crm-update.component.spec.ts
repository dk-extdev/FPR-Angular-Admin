/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Fpr360TestModule } from '../../../test.module';
import { ClientCrmUpdateComponent } from 'app/entities/client-crm/client-crm-update.component';
import { ClientCrmService } from 'app/entities/client-crm/client-crm.service';
import { ClientCrm } from 'app/shared/model/client-crm.model';

describe('Component Tests', () => {
    describe('ClientCrm Management Update Component', () => {
        let comp: ClientCrmUpdateComponent;
        let fixture: ComponentFixture<ClientCrmUpdateComponent>;
        let service: ClientCrmService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Fpr360TestModule],
                declarations: [ClientCrmUpdateComponent]
            })
                .overrideTemplate(ClientCrmUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ClientCrmUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClientCrmService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ClientCrm(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.clientCrm = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ClientCrm();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.clientCrm = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
