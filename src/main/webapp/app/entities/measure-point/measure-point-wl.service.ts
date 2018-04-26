import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { MeasurePointWl } from './measure-point-wl.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MeasurePointWlService {

    private resourceUrl = 'api/measure-points';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(measurePoint: MeasurePointWl): Observable<MeasurePointWl> {
        const copy = this.convert(measurePoint);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(measurePoint: MeasurePointWl): Observable<MeasurePointWl> {
        const copy = this.convert(measurePoint);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<MeasurePointWl> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
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
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.startTm = this.dateUtils
            .convertLocalDateFromServer(entity.startTm);
        entity.endTm = this.dateUtils
            .convertLocalDateFromServer(entity.endTm);
    }

    private convert(measurePoint: MeasurePointWl): MeasurePointWl {
        const copy: MeasurePointWl = Object.assign({}, measurePoint);
        copy.startTm = this.dateUtils
            .convertLocalDateToServer(measurePoint.startTm);
        copy.endTm = this.dateUtils
            .convertLocalDateToServer(measurePoint.endTm);
        return copy;
    }
}
