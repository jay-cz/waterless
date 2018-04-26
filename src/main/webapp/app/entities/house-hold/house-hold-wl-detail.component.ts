import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { HouseHoldWl } from './house-hold-wl.model';
import { HouseHoldWlService } from './house-hold-wl.service';

@Component({
    selector: 'jhi-house-hold-wl-detail',
    templateUrl: './house-hold-wl-detail.component.html'
})
export class HouseHoldWlDetailComponent implements OnInit, OnDestroy {

    houseHold: HouseHoldWl;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private houseHoldService: HouseHoldWlService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHouseHolds();
    }

    load(id) {
        this.houseHoldService.find(id).subscribe((houseHold) => {
            this.houseHold = houseHold;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHouseHolds() {
        this.eventSubscriber = this.eventManager.subscribe(
            'houseHoldListModification',
            (response) => this.load(this.houseHold.id)
        );
    }
}
