import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGuruClient } from 'app/shared/model/guru-client.model';

type EntityResponseType = HttpResponse<IGuruClient>;
type EntityArrayResponseType = HttpResponse<IGuruClient[]>;

@Injectable({ providedIn: 'root' })
export class GuruClientService {
    private resourceUrl = SERVER_API_URL + 'api/guru-clients';

    constructor(private http: HttpClient) {}

    create(guruClient: IGuruClient): Observable<EntityResponseType> {
        return this.http.post<IGuruClient>(this.resourceUrl, guruClient, { observe: 'response' });
    }

    update(guruClient: IGuruClient): Observable<EntityResponseType> {
        return this.http.put<IGuruClient>(this.resourceUrl, guruClient, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGuruClient>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGuruClient[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
