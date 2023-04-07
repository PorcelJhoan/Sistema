import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Producto } from '../models/producto';

@Injectable({
  providedIn: 'root'
})
export class ImagenService {

  productoURL = 'http://localhost:8080/producto/';

  constructor(private httpClient: HttpClient) { }

  
  public upload(file:File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('img',file);

    return this.httpClient.post<any>(this.productoURL + `imagen`,formData);
  }
 
  
}
