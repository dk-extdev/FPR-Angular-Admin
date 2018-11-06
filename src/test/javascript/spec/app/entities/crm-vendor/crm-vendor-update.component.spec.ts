/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Fpr360TestModule } from '../../../test.module';
import { CrmVendorUpdateComponent } from 'app/entities/crm-vendor/crm-vendor-update.component';
import { CrmVendorService } from 'app/entities/crm-vendor/crm-vendor.service';
import { CrmVendor } from 'app/shared/model/crm-vendor.model';

describe('Component Tests', () => {
    describe('CrmVendor Management Update Component', () => {
        let comp: CrmVendorUpdateComponent;
        let fixture: ComponentFixture<CrmVendorUpdateComponent>;
        let service: CrmVendorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Fpr360TestModule],
                declarations: [CrmVendorUpdateComponent]
            })
                .overrideTemplate(CrmVendorUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CrmVendorUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CrmVendorService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CrmVendor(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.crmVendor = entity;
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
                    const entity = new CrmVendor();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.crmVendor = entity;
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
