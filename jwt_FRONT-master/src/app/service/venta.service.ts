import { Injectable } from '@angular/core';
import { element } from 'protractor';
import { Producto } from '../models/producto';

@Injectable({
  providedIn: 'root'
})
export class VentaService {
  producto: Producto;
  items = [];
  encontrado = false;
  constructor() { }

  adicionar(producto) {
    this.encontrado = false;
    this.items.forEach((elemento) => {
      // tslint:disable-next-line:triple-equals
      if(elemento.id == producto.id) {
        this.encontrado = true;
        elemento.cantidad += 1;
      }
    });
    if(!this.encontrado){
      producto.areaid=1;
      producto.tipoproductoid=producto.precio;
      this.items.push(producto);
    }
  }
LimpiarCarrito(){
  this.items=[];
  return this.items;
}
ListarCarrito(){
  return this.items;
}

}
