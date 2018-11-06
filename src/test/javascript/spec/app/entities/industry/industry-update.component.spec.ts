/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Fpr360TestModule } from '../../../test.module';
import { IndustryUpdateComponent } from 'app/entities/industry/industry-update.component';
import { IndustryService } from 'app/entities/industry/industry.service';
import { Industry } from 'app/shared/model/industry.model';

describe('Component Tests', () => {
    describe('Industry Management Update Component', () => {
        let comp: IndustryUpdateComponent;
        let fixture: ComponentFixture<IndustryUpdateComponent>;
        let service: IndustryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Fpr360TestModule],
                declarations: [IndustryUpdateComponent]
            })
                .overrideTemplate(IndustryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IndustryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndustryService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Industry(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.industry = entity;
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
                    const entity = new Industry();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.industry = entity;
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
