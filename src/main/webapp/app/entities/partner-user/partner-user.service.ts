import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPartnerUser } from 'app/shared/model/partner-user.model';

type EntityResponseType = HttpResponse<IPartnerUser>;
type EntityArrayResponseType = HttpResponse<IPartnerUser[]>;

@Injectable({ providedIn: 'root' })
export class PartnerUserService {
    private resourceUrl = SERVER_API_URL + 'api/partner-users';

    constructor(private http: HttpClient) {}

    create(partnerUser: IPartnerUser): Observable<EntityResponseType> {
        return this.http.post<IPartnerUser>(this.resourceUrl, partnerUser, { observe: 'response' });
    }

    update(partnerUser: IPartnerUser): Observable<EntityResponseType> {
        return this.http.put<IPartnerUser>(this.resourceUrl, partnerUser, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPartnerUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPartnerUser[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
