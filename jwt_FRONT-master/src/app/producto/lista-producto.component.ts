import { Component, OnInit } from '@angular/core';
import { Producto } from '../models/producto';
import { ProductoService } from '../service/producto.service';
import { ToastrService } from 'ngx-toastr';
import { TokenService } from '../service/token.service';
import { VentaService } from '../service/venta.service';

@Component({
  selector: 'app-lista-producto',
  templateUrl: './lista-producto.component.html',
  styleUrls: ['./lista-producto.component.css']
})
export class ListaProductoComponent implements OnInit {
  filterPost = '';
  totalpages : Array<number>;
  productos: Producto[] = [];
  roles: string[];
  isAdmin = false;
  isFirst=false;
  islast=false;
  page = 0;
  size=10;
  order ="id";
  asc=true;

  producto:Producto;

  constructor(
    private productoService: ProductoService,
    private toastr: ToastrService,
    private tokenService: TokenService,
    private ventaService:VentaService
  ) { }

  ngOnInit() {
    this.cargarProductos();
    this.roles = this.tokenService.getAuthorities();
    this.roles.forEach(rol => {
      if (rol === 'ROLE_ADMIN') {
        this.isAdmin = true;
      }
    });
  }

  adicionarC(prod:Producto){
    if(prod.cantidad==0){
      this.toastr.error("Error", prod.nombre+' sin Stock', {
        timeOut: 3000,  positionClass: 'toast-top-center',
      });
    }else{
    this.ventaService.adicionar(prod);
    this.toastr.success('Producto Añadido', 'OK', {
      timeOut: 3000, positionClass: 'toast-top-center'
    });
    //window.alert(prod.nombre+" ,producto adicionado");
    console.log(prod.id);
    }
  }

  sort():void{
    this.asc=!this.asc;
    this.cargarProductos();

   }
rewind():void{
  if(!this.isFirst){
    this.page--;
    this.cargarProductos();
}
 }
forward():void{
  if(!this.islast){
    this.page++;
    this.cargarProductos();
}
 }
         

  cargarProductos(): void {
    this.productoService.lista(this.page,this.size,this.order,this.asc).subscribe(
      data => {
        this.productos = data['content'];
        this.isFirst=data['first'];
         this.islast=data['last'];
         console.log(data);
         this.totalpages = new Array(data['totalPages']);
      },
      err => {
        console.log(err);
      }
    );
  }
  setPage(page:number):void{
    this.page =page;
    this.cargarProductos();
    }

  borrar(id: number) {
    this.productoService.delete(id).subscribe(
      data => {
        this.toastr.success('Producto Eliminado', 'OK', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });
        this.cargarProductos();
      },
      err => {
        this.toastr.error(err.error.mensaje, 'Fail', {
          timeOut: 3000, positionClass: 'toast-top-center',
        });
      }
    );
  }

}
