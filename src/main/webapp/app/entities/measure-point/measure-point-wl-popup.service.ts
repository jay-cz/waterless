import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MeasurePointWl } from './measure-point-wl.model';
import { MeasurePointWlService } from './measure-point-wl.service';

@Injectable()
export class MeasurePointWlPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private measurePointService: MeasurePointWlService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.measurePointService.find(id).subscribe((measurePoint) => {
                    if (measurePoint.startTm) {
                        measurePoint.startTm = {
                            year: measurePoint.startTm.getFullYear(),
                            month: measurePoint.startTm.getMonth() + 1,
                            day: measurePoint.startTm.getDate()
                        };
                    }
                    if (measurePoint.endTm) {
                        measurePoint.endTm = {
                            year: measurePoint.endTm.getFullYear(),
                            month: measurePoint.endTm.getMonth() + 1,
                            day: measurePoint.endTm.getDate()
                        };
                    }
                    this.ngbModalRef = this.measurePointModalRef(component, measurePoint);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.measurePointModalRef(component, new MeasurePointWl());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    measurePointModalRef(component: Component, measurePoint: MeasurePointWl): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.measurePoint = measurePoint;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
