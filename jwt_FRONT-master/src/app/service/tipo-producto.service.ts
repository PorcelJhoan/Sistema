import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {TipoProducto } from '../models/tipoProducto';

@Injectable({
  providedIn: 'root'
})
export class TipoProductoService {


  areaURL ='http://localhost:8080/tipoproducto/';

  constructor(private httpClient: HttpClient) { }

  public lista(): Observable<TipoProducto[]> {
    return this.httpClient.get<TipoProducto[]>(this.areaURL + 'lista');
  }

  public detalle(id: number): Observable<TipoProducto> {
    return this.httpClient.get<TipoProducto>(this.areaURL + `detail/${id}`);
  }

  public nuevo(producto: TipoProducto): Observable<any> {
    return this.httpClient.post<any>(this.areaURL + 'create', producto);
  }

  public actualizar(id: number, producto: TipoProducto): Observable<any> {
    return this.httpClient.put<any>(this.areaURL + `update/${id}`, producto);
  }

  public eliminar(id: number): Observable<any> {
    return this.httpClient.delete<any>(this.areaURL + `delete/${id}`);
  }

}
