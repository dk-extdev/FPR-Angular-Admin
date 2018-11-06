import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPartnerClient } from 'app/shared/model/partner-client.model';

type EntityResponseType = HttpResponse<IPartnerClient>;
type EntityArrayResponseType = HttpResponse<IPartnerClient[]>;

@Injectable({ providedIn: 'root' })
export class PartnerClientService {
    private resourceUrl = SERVER_API_URL + 'api/partner-clients';

    constructor(private http: HttpClient) {}

    create(partnerClient: IPartnerClient): Observable<EntityResponseType> {
        return this.http.post<IPartnerClient>(this.resourceUrl, partnerClient, { observe: 'response' });
    }

    update(partnerClient: IPartnerClient): Observable<EntityResponseType> {
        return this.http.put<IPartnerClient>(this.resourceUrl, partnerClient, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPartnerClient>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPartnerClient[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
