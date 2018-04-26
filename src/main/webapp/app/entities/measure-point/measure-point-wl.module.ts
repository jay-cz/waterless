import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WaterlessSharedModule } from '../../shared';
import {
    MeasurePointWlService,
    MeasurePointWlPopupService,
    MeasurePointWlComponent,
    MeasurePointWlDetailComponent,
    MeasurePointWlDialogComponent,
    MeasurePointWlPopupComponent,
    MeasurePointWlDeletePopupComponent,
    MeasurePointWlDeleteDialogComponent,
    measurePointRoute,
    measurePointPopupRoute,
} from './';

const ENTITY_STATES = [
    ...measurePointRoute,
    ...measurePointPopupRoute,
];

@NgModule({
    imports: [
        WaterlessSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MeasurePointWlComponent,
        MeasurePointWlDetailComponent,
        MeasurePointWlDialogComponent,
        MeasurePointWlDeleteDialogComponent,
        MeasurePointWlPopupComponent,
        MeasurePointWlDeletePopupComponent,
    ],
    entryComponents: [
        MeasurePointWlComponent,
        MeasurePointWlDialogComponent,
        MeasurePointWlPopupComponent,
        MeasurePointWlDeleteDialogComponent,
        MeasurePointWlDeletePopupComponent,
    ],
    providers: [
        MeasurePointWlService,
        MeasurePointWlPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WaterlessMeasurePointWlModule {}
