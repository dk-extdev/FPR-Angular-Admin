import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IClientCrm } from 'app/shared/model/client-crm.model';

type EntityResponseType = HttpResponse<IClientCrm>;
type EntityArrayResponseType = HttpResponse<IClientCrm[]>;

@Injectable({ providedIn: 'root' })
export class ClientCrmService {
    private resourceUrl = SERVER_API_URL + 'api/client-crms';

    constructor(private http: HttpClient) {}

    create(clientCrm: IClientCrm): Observable<EntityResponseType> {
        return this.http.post<IClientCrm>(this.resourceUrl, clientCrm, { observe: 'response' });
    }

    update(clientCrm: IClientCrm): Observable<EntityResponseType> {
        return this.http.put<IClientCrm>(this.resourceUrl, clientCrm, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IClientCrm>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IClientCrm[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
