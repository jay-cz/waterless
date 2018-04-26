/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WaterlessTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MeasurePointWlDetailComponent } from '../../../../../../main/webapp/app/entities/measure-point/measure-point-wl-detail.component';
import { MeasurePointWlService } from '../../../../../../main/webapp/app/entities/measure-point/measure-point-wl.service';
import { MeasurePointWl } from '../../../../../../main/webapp/app/entities/measure-point/measure-point-wl.model';

describe('Component Tests', () => {

    describe('MeasurePointWl Management Detail Component', () => {
        let comp: MeasurePointWlDetailComponent;
        let fixture: ComponentFixture<MeasurePointWlDetailComponent>;
        let service: MeasurePointWlService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WaterlessTestModule],
                declarations: [MeasurePointWlDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MeasurePointWlService,
                    JhiEventManager
                ]
            }).overrideTemplate(MeasurePointWlDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MeasurePointWlDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MeasurePointWlService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MeasurePointWl(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.measurePoint).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
