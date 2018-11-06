import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICrmVendor } from 'app/shared/model/crm-vendor.model';

type EntityResponseType = HttpResponse<ICrmVendor>;
type EntityArrayResponseType = HttpResponse<ICrmVendor[]>;

@Injectable({ providedIn: 'root' })
export class CrmVendorService {
    private resourceUrl = SERVER_API_URL + 'api/crm-vendors';

    constructor(private http: HttpClient) {}

    create(crmVendor: ICrmVendor): Observable<EntityResponseType> {
        return this.http.post<ICrmVendor>(this.resourceUrl, crmVendor, { observe: 'response' });
    }

    update(crmVendor: ICrmVendor): Observable<EntityResponseType> {
        return this.http.put<ICrmVendor>(this.resourceUrl, crmVendor, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICrmVendor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICrmVendor[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
