/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Fpr360TestModule } from '../../../test.module';
import { CrmVendorDetailComponent } from 'app/entities/crm-vendor/crm-vendor-detail.component';
import { CrmVendor } from 'app/shared/model/crm-vendor.model';

describe('Component Tests', () => {
    describe('CrmVendor Management Detail Component', () => {
        let comp: CrmVendorDetailComponent;
        let fixture: ComponentFixture<CrmVendorDetailComponent>;
        const route = ({ data: of({ crmVendor: new CrmVendor(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Fpr360TestModule],
                declarations: [CrmVendorDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CrmVendorDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CrmVendorDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.crmVendor).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
