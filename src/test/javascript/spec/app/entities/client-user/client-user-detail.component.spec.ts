/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Fpr360TestModule } from '../../../test.module';
import { ClientUserDetailComponent } from 'app/entities/client-user/client-user-detail.component';
import { ClientUser } from 'app/shared/model/client-user.model';

describe('Component Tests', () => {
    describe('ClientUser Management Detail Component', () => {
        let comp: ClientUserDetailComponent;
        let fixture: ComponentFixture<ClientUserDetailComponent>;
        const route = ({ data: of({ clientUser: new ClientUser(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Fpr360TestModule],
                declarations: [ClientUserDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ClientUserDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClientUserDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.clientUser).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
