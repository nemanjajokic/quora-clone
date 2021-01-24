import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class ImageService {

    private baseUrl = "http://localhost:8080/api/images";

    constructor(private http: HttpClient) { }

    public uploadImage(formData: FormData): Observable<any> {
        const file = formData.get("file") as File;
        const url = this.baseUrl + `/upload?file=${file.name}`;

        return this.http.post(url, formData, { responseType: "text" });
    }

}
