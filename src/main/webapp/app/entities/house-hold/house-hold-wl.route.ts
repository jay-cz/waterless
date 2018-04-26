import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { HouseHoldWlComponent } from './house-hold-wl.component';
import { HouseHoldWlDetailComponent } from './house-hold-wl-detail.component';
import { HouseHoldWlPopupComponent } from './house-hold-wl-dialog.component';
import { HouseHoldWlDeletePopupComponent } from './house-hold-wl-delete-dialog.component';

@Injectable()
export class HouseHoldWlResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const houseHoldRoute: Routes = [
    {
        path: 'house-hold-wl',
        component: HouseHoldWlComponent,
        resolve: {
            'pagingParams': HouseHoldWlResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HouseHolds'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'house-hold-wl/:id',
        component: HouseHoldWlDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HouseHolds'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const houseHoldPopupRoute: Routes = [
    {
        path: 'house-hold-wl-new',
        component: HouseHoldWlPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HouseHolds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'house-hold-wl/:id/edit',
        component: HouseHoldWlPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HouseHolds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'house-hold-wl/:id/delete',
        component: HouseHoldWlDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HouseHolds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
