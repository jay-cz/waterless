/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WaterlessTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { HouseHoldWlDetailComponent } from '../../../../../../main/webapp/app/entities/house-hold/house-hold-wl-detail.component';
import { HouseHoldWlService } from '../../../../../../main/webapp/app/entities/house-hold/house-hold-wl.service';
import { HouseHoldWl } from '../../../../../../main/webapp/app/entities/house-hold/house-hold-wl.model';

describe('Component Tests', () => {

    describe('HouseHoldWl Management Detail Component', () => {
        let comp: HouseHoldWlDetailComponent;
        let fixture: ComponentFixture<HouseHoldWlDetailComponent>;
        let service: HouseHoldWlService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WaterlessTestModule],
                declarations: [HouseHoldWlDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    HouseHoldWlService,
                    JhiEventManager
                ]
            }).overrideTemplate(HouseHoldWlDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HouseHoldWlDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HouseHoldWlService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new HouseHoldWl(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.houseHold).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
