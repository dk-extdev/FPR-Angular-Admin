/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Fpr360TestModule } from '../../../test.module';
import { PartnerUserDetailComponent } from 'app/entities/partner-user/partner-user-detail.component';
import { PartnerUser } from 'app/shared/model/partner-user.model';

describe('Component Tests', () => {
    describe('PartnerUser Management Detail Component', () => {
        let comp: PartnerUserDetailComponent;
        let fixture: ComponentFixture<PartnerUserDetailComponent>;
        const route = ({ data: of({ partnerUser: new PartnerUser(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Fpr360TestModule],
                declarations: [PartnerUserDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PartnerUserDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PartnerUserDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.partnerUser).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
