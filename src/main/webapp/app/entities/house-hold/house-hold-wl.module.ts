import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WaterlessSharedModule } from '../../shared';
import {
    HouseHoldWlService,
    HouseHoldWlPopupService,
    HouseHoldWlComponent,
    HouseHoldWlDetailComponent,
    HouseHoldWlDialogComponent,
    HouseHoldWlPopupComponent,
    HouseHoldWlDeletePopupComponent,
    HouseHoldWlDeleteDialogComponent,
    houseHoldRoute,
    houseHoldPopupRoute,
    HouseHoldWlResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...houseHoldRoute,
    ...houseHoldPopupRoute,
];

@NgModule({
    imports: [
        WaterlessSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        HouseHoldWlComponent,
        HouseHoldWlDetailComponent,
        HouseHoldWlDialogComponent,
        HouseHoldWlDeleteDialogComponent,
        HouseHoldWlPopupComponent,
        HouseHoldWlDeletePopupComponent,
    ],
    entryComponents: [
        HouseHoldWlComponent,
        HouseHoldWlDialogComponent,
        HouseHoldWlPopupComponent,
        HouseHoldWlDeleteDialogComponent,
        HouseHoldWlDeletePopupComponent,
    ],
    providers: [
        HouseHoldWlService,
        HouseHoldWlPopupService,
        HouseHoldWlResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WaterlessHouseHoldWlModule {}
