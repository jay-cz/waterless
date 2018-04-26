import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { HouseHoldWl } from './house-hold-wl.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class HouseHoldWlService {

    private resourceUrl = 'api/house-holds';

    constructor(private http: Http) { }

    create(houseHold: HouseHoldWl): Observable<HouseHoldWl> {
        const copy = this.convert(houseHold);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(houseHold: HouseHoldWl): Observable<HouseHoldWl> {
        const copy = this.convert(houseHold);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<HouseHoldWl> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(houseHold: HouseHoldWl): HouseHoldWl {
        const copy: HouseHoldWl = Object.assign({}, houseHold);
        return copy;
    }
}
