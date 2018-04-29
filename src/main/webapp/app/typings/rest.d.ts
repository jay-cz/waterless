/* tslint:disable */
// Generated using typescript-generator version 2.2.413 on 2018-04-29 13:19:24.

export interface HouseHoldDTO extends Serializable {
    id: number;
    streetAddress: string;
    houseNumber: string;
    postalCode: string;
    city: string;
    flatNumber: number;
    numberOfPeople: number;
    latitude: number;
    longitude: number;
}

export interface MeasureDataDTO {
    id: number;
    startTm: Date;
    endTm: Date;
    latitude: number;
    longitude: number;
    measureValue: MeasureEnum;
}

export interface MeasurePointDTO extends Serializable {
    id: number;
    startTm: Date;
    endTm: Date;
    ipAddress: string;
    value: string;
    houseHoldId: number;
}

export interface UserDTO {
    id: number;
    login: string;
    firstName: string;
    lastName: string;
    email: string;
    imageUrl: string;
    activated: boolean;
    langKey: string;
    createdBy: string;
    createdDate: Date;
    lastModifiedBy: string;
    lastModifiedDate: Date;
    authorities: string[];
}

export interface KeyAndPasswordVM {
    key: string;
    newPassword: string;
}

export interface LoggerVM {
    name: string;
    level: string;
}

export interface LoginVM {
    username: string;
    password: string;
    rememberMe: boolean;
}

export interface ManagedUserVM extends UserDTO {
    password: string;
}

export interface Serializable {
}

export type MeasureEnum = "WATERLESS" | "SO_SO" | "FLOWS";
