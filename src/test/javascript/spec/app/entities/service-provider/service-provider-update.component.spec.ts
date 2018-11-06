/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Fpr360TestModule } from '../../../test.module';
import { ServiceProviderUpdateComponent } from 'app/entities/service-provider/service-provider-update.component';
import { ServiceProviderService } from 'app/entities/service-provider/service-provider.service';
import { ServiceProvider } from 'app/shared/model/service-provider.model';

describe('Component Tests', () => {
    describe('ServiceProvider Management Update Component', () => {
        let comp: ServiceProviderUpdateComponent;
        let fixture: ComponentFixture<ServiceProviderUpdateComponent>;
        let service: ServiceProviderService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Fpr360TestModule],
                declarations: [ServiceProviderUpdateComponent]
            })
                .overrideTemplate(ServiceProviderUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ServiceProviderUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServiceProviderService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ServiceProvider(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.serviceProvider = entity;
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
                    const entity = new ServiceProvider();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.serviceProvider = entity;
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
