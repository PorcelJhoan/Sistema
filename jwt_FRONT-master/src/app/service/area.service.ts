import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Area } from '../models/area';
@Injectable({
  providedIn: 'root'
})
export class AreaService {

  areaURL ='http://localhost:8080/area/';

  constructor(private httpClient: HttpClient) { }

  public lista(): Observable<Area[]> {
    return this.httpClient.get<Area[]>(this.areaURL + 'lista');
  }

  public detalle(id: number): Observable<Area> {
    return this.httpClient.get<Area>(this.areaURL + `detail/${id}`);
  }

  public nuevo(producto: Area): Observable<any> {
    return this.httpClient.post<any>(this.areaURL + 'create', producto);
  }

  public actualizar(id: number, producto: Area): Observable<any> {
    return this.httpClient.put<any>(this.areaURL + `update/${id}`, producto);
  }

  public eliminar(id: number): Observable<any> {
    return this.httpClient.delete<any>(this.areaURL + `delete/${id}`);
  }
}
