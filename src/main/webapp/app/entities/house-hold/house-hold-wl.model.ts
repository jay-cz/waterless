import { BaseEntity } from './../../shared';

export class HouseHoldWl implements BaseEntity {
    constructor(
        public id?: number,
        public streetAddress?: string,
        public houseNumber?: string,
        public postalCode?: string,
        public city?: string,
        public flatNumber?: number,
        public numberOfPeople?: number,
        public latitude?: number,
        public longitude?: number,
        public houseHolds?: BaseEntity[],
    ) {
    }
}
