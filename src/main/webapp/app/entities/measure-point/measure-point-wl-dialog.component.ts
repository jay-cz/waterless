import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MeasurePointWl } from './measure-point-wl.model';
import { MeasurePointWlPopupService } from './measure-point-wl-popup.service';
import { MeasurePointWlService } from './measure-point-wl.service';
import { HouseHoldWl, HouseHoldWlService } from '../house-hold';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-measure-point-wl-dialog',
    templateUrl: './measure-point-wl-dialog.component.html'
})
export class MeasurePointWlDialogComponent implements OnInit {

    measurePoint: MeasurePointWl;
    isSaving: boolean;

    households: HouseHoldWl[];
    startTmDp: any;
    endTmDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private measurePointService: MeasurePointWlService,
        private houseHoldService: HouseHoldWlService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.houseHoldService.query()
            .subscribe((res: ResponseWrapper) => { this.households = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.measurePoint.id !== undefined) {
            this.subscribeToSaveResponse(
                this.measurePointService.update(this.measurePoint));
        } else {
            this.subscribeToSaveResponse(
                this.measurePointService.create(this.measurePoint));
        }
    }

    private subscribeToSaveResponse(result: Observable<MeasurePointWl>) {
        result.subscribe((res: MeasurePointWl) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MeasurePointWl) {
        this.eventManager.broadcast({ name: 'measurePointListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackHouseHoldById(index: number, item: HouseHoldWl) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-measure-point-wl-popup',
    template: ''
})
export class MeasurePointWlPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private measurePointPopupService: MeasurePointWlPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.measurePointPopupService
                    .open(MeasurePointWlDialogComponent as Component, params['id']);
            } else {
                this.measurePointPopupService
                    .open(MeasurePointWlDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
