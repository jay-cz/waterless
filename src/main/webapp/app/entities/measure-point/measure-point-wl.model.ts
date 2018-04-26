import { BaseEntity } from './../../shared';

export class MeasurePointWl implements BaseEntity {
    constructor(
        public id?: number,
        public startTm?: any,
        public endTm?: any,
        public ipAddress?: string,
        public value?: string,
        public houseHoldId?: number,
    ) {
    }
}
