/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Fpr360TestModule } from '../../../test.module';
import { PartnerClientUpdateComponent } from 'app/entities/partner-client/partner-client-update.component';
import { PartnerClientService } from 'app/entities/partner-client/partner-client.service';
import { PartnerClient } from 'app/shared/model/partner-client.model';

describe('Component Tests', () => {
    describe('PartnerClient Management Update Component', () => {
        let comp: PartnerClientUpdateComponent;
        let fixture: ComponentFixture<PartnerClientUpdateComponent>;
        let service: PartnerClientService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Fpr360TestModule],
                declarations: [PartnerClientUpdateComponent]
            })
                .overrideTemplate(PartnerClientUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PartnerClientUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartnerClientService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PartnerClient(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.partnerClient = entity;
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
                    const entity = new PartnerClient();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.partnerClient = entity;
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
