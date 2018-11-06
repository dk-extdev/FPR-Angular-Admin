/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Fpr360TestModule } from '../../../test.module';
import { PartnerClientDetailComponent } from 'app/entities/partner-client/partner-client-detail.component';
import { PartnerClient } from 'app/shared/model/partner-client.model';

describe('Component Tests', () => {
    describe('PartnerClient Management Detail Component', () => {
        let comp: PartnerClientDetailComponent;
        let fixture: ComponentFixture<PartnerClientDetailComponent>;
        const route = ({ data: of({ partnerClient: new PartnerClient(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Fpr360TestModule],
                declarations: [PartnerClientDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PartnerClientDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PartnerClientDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.partnerClient).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
