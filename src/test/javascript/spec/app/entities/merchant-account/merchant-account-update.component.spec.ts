/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Fpr360TestModule } from '../../../test.module';
import { MerchantAccountUpdateComponent } from 'app/entities/merchant-account/merchant-account-update.component';
import { MerchantAccountService } from 'app/entities/merchant-account/merchant-account.service';
import { MerchantAccount } from 'app/shared/model/merchant-account.model';

describe('Component Tests', () => {
    describe('MerchantAccount Management Update Component', () => {
        let comp: MerchantAccountUpdateComponent;
        let fixture: ComponentFixture<MerchantAccountUpdateComponent>;
        let service: MerchantAccountService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Fpr360TestModule],
                declarations: [MerchantAccountUpdateComponent]
            })
                .overrideTemplate(MerchantAccountUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MerchantAccountUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MerchantAccountService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new MerchantAccount(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.merchantAccount = entity;
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
                    const entity = new MerchantAccount();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.merchantAccount = entity;
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
