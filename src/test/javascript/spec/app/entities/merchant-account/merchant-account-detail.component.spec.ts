/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Fpr360TestModule } from '../../../test.module';
import { MerchantAccountDetailComponent } from 'app/entities/merchant-account/merchant-account-detail.component';
import { MerchantAccount } from 'app/shared/model/merchant-account.model';

describe('Component Tests', () => {
    describe('MerchantAccount Management Detail Component', () => {
        let comp: MerchantAccountDetailComponent;
        let fixture: ComponentFixture<MerchantAccountDetailComponent>;
        const route = ({ data: of({ merchantAccount: new MerchantAccount(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Fpr360TestModule],
                declarations: [MerchantAccountDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MerchantAccountDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MerchantAccountDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.merchantAccount).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
