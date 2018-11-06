/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Fpr360TestModule } from '../../../test.module';
import { MerchantDetailComponent } from 'app/entities/merchant/merchant-detail.component';
import { Merchant } from 'app/shared/model/merchant.model';

describe('Component Tests', () => {
    describe('Merchant Management Detail Component', () => {
        let comp: MerchantDetailComponent;
        let fixture: ComponentFixture<MerchantDetailComponent>;
        const route = ({ data: of({ merchant: new Merchant(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Fpr360TestModule],
                declarations: [MerchantDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MerchantDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MerchantDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.merchant).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
