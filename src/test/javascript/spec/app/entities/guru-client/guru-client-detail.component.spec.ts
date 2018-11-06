/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Fpr360TestModule } from '../../../test.module';
import { GuruClientDetailComponent } from 'app/entities/guru-client/guru-client-detail.component';
import { GuruClient } from 'app/shared/model/guru-client.model';

describe('Component Tests', () => {
    describe('GuruClient Management Detail Component', () => {
        let comp: GuruClientDetailComponent;
        let fixture: ComponentFixture<GuruClientDetailComponent>;
        const route = ({ data: of({ guruClient: new GuruClient(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Fpr360TestModule],
                declarations: [GuruClientDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GuruClientDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GuruClientDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.guruClient).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
