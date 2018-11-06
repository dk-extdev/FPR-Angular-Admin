/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Fpr360TestModule } from '../../../test.module';
import { ClientCrmDetailComponent } from 'app/entities/client-crm/client-crm-detail.component';
import { ClientCrm } from 'app/shared/model/client-crm.model';

describe('Component Tests', () => {
    describe('ClientCrm Management Detail Component', () => {
        let comp: ClientCrmDetailComponent;
        let fixture: ComponentFixture<ClientCrmDetailComponent>;
        const route = ({ data: of({ clientCrm: new ClientCrm(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Fpr360TestModule],
                declarations: [ClientCrmDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ClientCrmDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClientCrmDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.clientCrm).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
