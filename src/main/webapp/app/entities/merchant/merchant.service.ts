import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMerchant } from 'app/shared/model/merchant.model';

type EntityResponseType = HttpResponse<IMerchant>;
type EntityArrayResponseType = HttpResponse<IMerchant[]>;

@Injectable({ providedIn: 'root' })
export class MerchantService {
    private resourceUrl = SERVER_API_URL + 'api/merchants';

    constructor(private http: HttpClient) {}

    create(merchant: IMerchant): Observable<EntityResponseType> {
        return this.http.post<IMerchant>(this.resourceUrl, merchant, { observe: 'response' });
    }

    update(merchant: IMerchant): Observable<EntityResponseType> {
        return this.http.put<IMerchant>(this.resourceUrl, merchant, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMerchant>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMerchant[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
