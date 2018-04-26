import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MeasurePointWl } from './measure-point-wl.model';
import { MeasurePointWlService } from './measure-point-wl.service';

@Component({
    selector: 'jhi-measure-point-wl-detail',
    templateUrl: './measure-point-wl-detail.component.html'
})
export class MeasurePointWlDetailComponent implements OnInit, OnDestroy {

    measurePoint: MeasurePointWl;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private measurePointService: MeasurePointWlService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMeasurePoints();
    }

    load(id) {
        this.measurePointService.find(id).subscribe((measurePoint) => {
            this.measurePoint = measurePoint;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMeasurePoints() {
        this.eventSubscriber = this.eventManager.subscribe(
            'measurePointListModification',
            (response) => this.load(this.measurePoint.id)
        );
    }
}
