import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MeasurePointWlComponent } from './measure-point-wl.component';
import { MeasurePointWlDetailComponent } from './measure-point-wl-detail.component';
import { MeasurePointWlPopupComponent } from './measure-point-wl-dialog.component';
import { MeasurePointWlDeletePopupComponent } from './measure-point-wl-delete-dialog.component';

export const measurePointRoute: Routes = [
    {
        path: 'measure-point-wl',
        component: MeasurePointWlComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MeasurePoints'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'measure-point-wl/:id',
        component: MeasurePointWlDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MeasurePoints'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const measurePointPopupRoute: Routes = [
    {
        path: 'measure-point-wl-new',
        component: MeasurePointWlPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MeasurePoints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'measure-point-wl/:id/edit',
        component: MeasurePointWlPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MeasurePoints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'measure-point-wl/:id/delete',
        component: MeasurePointWlDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MeasurePoints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
