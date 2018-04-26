import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { HouseHoldWl } from './house-hold-wl.model';
import { HouseHoldWlPopupService } from './house-hold-wl-popup.service';
import { HouseHoldWlService } from './house-hold-wl.service';

@Component({
    selector: 'jhi-house-hold-wl-dialog',
    templateUrl: './house-hold-wl-dialog.component.html'
})
export class HouseHoldWlDialogComponent implements OnInit {

    houseHold: HouseHoldWl;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private houseHoldService: HouseHoldWlService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.houseHold.id !== undefined) {
            this.subscribeToSaveResponse(
                this.houseHoldService.update(this.houseHold));
        } else {
            this.subscribeToSaveResponse(
                this.houseHoldService.create(this.houseHold));
        }
    }

    private subscribeToSaveResponse(result: Observable<HouseHoldWl>) {
        result.subscribe((res: HouseHoldWl) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: HouseHoldWl) {
        this.eventManager.broadcast({ name: 'houseHoldListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-house-hold-wl-popup',
    template: ''
})
export class HouseHoldWlPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private houseHoldPopupService: HouseHoldWlPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.houseHoldPopupService
                    .open(HouseHoldWlDialogComponent as Component, params['id']);
            } else {
                this.houseHoldPopupService
                    .open(HouseHoldWlDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
