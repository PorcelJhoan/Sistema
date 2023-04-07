import { Component, OnInit } from '@angular/core';
import { ProductoService } from '../service/producto.service';
import { Producto } from '../models/producto';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { Area } from 'src/app/models/area';
import { TipoProducto } from 'src/app/models/tipoProducto';
import { AreaService } from '../service/area.service';
import { TipoProductoService } from '../service/tipo-producto.service';
import {Observable} from 'rxjs'
import { ImagenService } from '../service/imagen.service';
import { DomSanitizer } from '@angular/platform-browser';
import { Imagen } from '../models/imagen';
@Component({
  selector: 'app-nuevo-producto',
  templateUrl: './nuevo-producto.component.html',
  styleUrls: ['./nuevo-producto.component.css']
})
export class NuevoProductoComponent implements OnInit {
  public archivos:any=[];
  SelectFiles?:FileList;
  progressInfo =[];
  message ="";
  filename="";
  fileInfos:Observable<any>;
 public prev:string;
 nombreimagen ='default.jpg';
  nombre = '';
  precio: number = null;
  area :Area[] = [];
  tipoproducto :TipoProducto[] = [];
  areaid = 1;
  tipoproductoid = 1;
  nombreArea='';
  
  caracteristica='';
  cantidad = null;
  medida = '';
 

  filterPost = '';
  constructor(
    private productoService: ProductoService,
    private toastr: ToastrService,
    private router: Router,
    private areaService:AreaService,
    private tipoProductoService:TipoProductoService,
    private uploadService:ImagenService,
    private sanitizer:DomSanitizer
    ) { }

  ngOnInit() {
    this.areaService.lista().subscribe(
      data=>{
        this.area =data;
        console.log(this.area);
      },
      err => {
        console.log("error 1 : ",err);
      }
    );
    this.tipoProductoService.lista().subscribe(
      data=>{
        this.tipoproducto=data;
        console.log(this.tipoproducto);
      },
      err =>{
        console.log("error 2 : ",err);
      }
    )
  }

  selectFiles(event){
    this.progressInfo = [];
    event.target.files.length == 1 ? this.filename = event.target.files[0].name : this.filename = event.target.file.length+"archivos";
    this.archivos.push(event.target.files[0])
    this.SelectFiles = event.target.files;
    this.extraerBase64(event.target.files[0]).then( (imagen:any)=>{
      console.log(imagen);
      this.prev=imagen.base;
    })

  }


  
  upload(){
    
console.log("tamaño : ",this.SelectFiles);
    if(this.SelectFiles !=undefined){
    
    this.uploadService.upload(this.SelectFiles[0]).subscribe(
      data => {
        console.log(data);
       this.nombreimagen = data['imagen'];
      
       if(data != ""){
        this.onCreate();
       }
       
      },
      err => {
        console.log(err);
        this.toastr.error(err.error.mensaje, 'Fail IMAGE' , {
          timeOut: 3000,  positionClass: 'toast-top-center',
        });
        // this.router.navigate(['/']);
      }
      

    );
    }else{
      this.onCreate();
    }
  }

  onCreate(): void {
    const producto = new Producto(this.nombre, this.precio,this.caracteristica,this.cantidad,this.medida,this.areaid,this.tipoproductoid,this.nombreimagen);
    this.productoService.save(producto).subscribe(
      data => {
        this.toastr.success('Producto Creado', 'OK', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });
        this.router.navigate(['/lista']);
      },
      err => {
        this.toastr.error(err.error.mensaje, 'Fail', {
          timeOut: 3000,  positionClass: 'toast-top-center',
        });
        // this.router.navigate(['/']);
      }
    );
  }


  extraerBase64=async($event:any)=>new Promise((resolve,reject)=>{
    try{
       const unsafeImg=window.URL.createObjectURL($event);
       const image=this.sanitizer.bypassSecurityTrustUrl(unsafeImg);
       const reader=new FileReader();
       reader.readAsDataURL($event);
       reader.onload=()=>{
         resolve({
           base:reader.result
        });
      };
       reader.onerror= error=>{
         resolve({
           base:null
      });
      
    };
  } catch(e){
      return null;
    }
  })


}
