/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Fpr360TestModule } from '../../../test.module';
import { PartnerUserUpdateComponent } from 'app/entities/partner-user/partner-user-update.component';
import { PartnerUserService } from 'app/entities/partner-user/partner-user.service';
import { PartnerUser } from 'app/shared/model/partner-user.model';

describe('Component Tests', () => {
    describe('PartnerUser Management Update Component', () => {
        let comp: PartnerUserUpdateComponent;
        let fixture: ComponentFixture<PartnerUserUpdateComponent>;
        let service: PartnerUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Fpr360TestModule],
                declarations: [PartnerUserUpdateComponent]
            })
                .overrideTemplate(PartnerUserUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PartnerUserUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartnerUserService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PartnerUser(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.partnerUser = entity;
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
                    const entity = new PartnerUser();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.partnerUser = entity;
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
