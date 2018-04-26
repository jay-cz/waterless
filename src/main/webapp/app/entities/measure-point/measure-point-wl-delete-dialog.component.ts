import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MeasurePointWl } from './measure-point-wl.model';
import { MeasurePointWlPopupService } from './measure-point-wl-popup.service';
import { MeasurePointWlService } from './measure-point-wl.service';

@Component({
    selector: 'jhi-measure-point-wl-delete-dialog',
    templateUrl: './measure-point-wl-delete-dialog.component.html'
})
export class MeasurePointWlDeleteDialogComponent {

    measurePoint: MeasurePointWl;

    constructor(
        private measurePointService: MeasurePointWlService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.measurePointService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'measurePointListModification',
                content: 'Deleted an measurePoint'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-measure-point-wl-delete-popup',
    template: ''
})
export class MeasurePointWlDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private measurePointPopupService: MeasurePointWlPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.measurePointPopupService
                .open(MeasurePointWlDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
