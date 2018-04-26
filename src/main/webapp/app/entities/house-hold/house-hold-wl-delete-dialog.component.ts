import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { HouseHoldWl } from './house-hold-wl.model';
import { HouseHoldWlPopupService } from './house-hold-wl-popup.service';
import { HouseHoldWlService } from './house-hold-wl.service';

@Component({
    selector: 'jhi-house-hold-wl-delete-dialog',
    templateUrl: './house-hold-wl-delete-dialog.component.html'
})
export class HouseHoldWlDeleteDialogComponent {

    houseHold: HouseHoldWl;

    constructor(
        private houseHoldService: HouseHoldWlService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.houseHoldService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'houseHoldListModification',
                content: 'Deleted an houseHold'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-house-hold-wl-delete-popup',
    template: ''
})
export class HouseHoldWlDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private houseHoldPopupService: HouseHoldWlPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.houseHoldPopupService
                .open(HouseHoldWlDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
