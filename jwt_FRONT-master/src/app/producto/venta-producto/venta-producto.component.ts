import { Component, OnInit } from '@angular/core';
import { Producto } from '../../models/producto';
import { ProductoService } from '../../service/producto.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import {VentaService} from '../../service/venta.service';
import { Utils } from 'src/app/producto/util/util';
import pdfMake from 'pdfmake/build/pdfMake';

import pdfFonts from 'pdfmake/build/vfs_fonts';
import * as es6printJS from "print-js";
import * as printJS from 'print-js';
import * as print from 'print-js';

pdfMake.vfs = pdfFonts.pdfMake.vfs;

@Component({
  selector: 'app-venta-producto',
  templateUrl: './venta-producto.component.html',
  styleUrls: ['./venta-producto.component.css']
})
export class VentaProductoComponent implements OnInit {

  nombre = 'HOLA' ;
  producto: Producto = null;
  items = [];
  mymodel:number;
  total:number=0;
  to=true;
  logoDataUrl: string;

  constructor(
    private productoService: ProductoService,
    private activatedRoute: ActivatedRoute,
    private toastr: ToastrService,
    private router: Router,
    private ventaService:VentaService
  ) { }

  ngOnInit() {
    this.items= this.ventaService.ListarCarrito();
    this.items.forEach(element => {
      this.total=this.total + element.tipoproductoid;
     });
    console.log(this.items.length);
    Utils.getImageDataUrlFromLocalPath1('assets/imgnaranja.png').then(
      result => this.logoDataUrl = result
    )

  }


createPdf(){
  const pdfDefinition:any = {
    styles: {
      header: {
        fontSize: 55,
        bold: true
      },
      anotherStyle: {
        italics: true,
        alignment: 'right'
      },
      content:{
        background:'red',
        color:'Blue'
      }
    },

    content :[
      {

        image: this.logoDataUrl ,
        width: 30,
        height: 30,
        margin: [ 35, -2 ]
      },

      { text: 'Negocio Ferreteria', margin: [ 5, 5, 10, 20 ] },
      { text: 'Nombre : ', margin: [ 5, 5, 10, 20 ] },
      { text: this.nombre, margin: [ 75, -35, 10, 20 ] },
      { text: 'fecha : ', margin: [ 280, -35, 10, 20 ] },
      { text: '12/12/1525', margin: [ 355, -35, 10, 20 ] },
      {
        table :{
          widths: [25,200,65,95,55],
          body :[
            [
              'Nro','Nombre','Precio','Cantidad','Total'
            ],
            [
              'col 11','col 22','col 33','col 22','col 33'
            ],
          ]
        },

      },
      { qr: 'text in QR', foreground: 'orange',alignment:'right',margin:[ 1, 35, 10, 20 ]},

    ]
  }
  const pdf = pdfMake.createPdf( pdfDefinition );
  pdf.print();
}


eliminar(item){
  this.items.forEach(element => {
  if(item.id == element.id){
    var indice = this.items.indexOf(element);
    this.items.splice(indice,1);
    this.total=0;
    this.items.forEach(element => {
      this.total=this.total + element.tipoproductoid;
     });
  }


   });
}

  valuechange(newValue, item) {
    this.to=!this.to;
  if(this.to){
    this.total=0;
    item.tipoproductoid = newValue*item.precio;
    item.areaid = newValue;
    this.items.forEach(element => {
     this.total=this.total + element.tipoproductoid;
    });
     console.log(this.items);

  }



  }

  printTest() {
    print({
      printable: "test",
      type: "html",
      style:
        "img{margin-top:5px;margin-left:40px;} #test {visibility: visible;font-size: 30px;color: green;} h1 { color: red; }",
      css: "src/style.css",
      onPrintDialogClose: () => alert("Print job complete.")
    });


  }
}
