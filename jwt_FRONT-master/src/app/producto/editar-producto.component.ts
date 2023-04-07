import { Component, OnInit } from '@angular/core';
import { Producto } from '../models/producto';
import { ProductoService } from '../service/producto.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Area } from 'src/app/models/area';
import { TipoProducto } from 'src/app/models/tipoProducto';
import { AreaService } from '../service/area.service';
import { TipoProductoService } from '../service/tipo-producto.service';
import {Observable} from 'rxjs'
import { ImagenService } from '../service/imagen.service';
import { DomSanitizer } from '@angular/platform-browser';
import { Imagen } from '../models/imagen';
@Component({
  selector: 'app-editar-producto',
  templateUrl: './editar-producto.component.html',
  styleUrls: ['./editar-producto.component.css']
})
export class EditarProductoComponent implements OnInit {

  producto: Producto = null;
  area :Area[] = [];
  tipoproducto :TipoProducto[] = [];
  areaid = 0;
  tipoproductoid = 0;
  areat ="";
  tipot = "";
  path:string;

  public archivos:any=[];
  SelectFiles:FileList;
  progressInfo =[];
  message ="";
  filename='default.jpg';
  fileInfos:Observable<any>;
 public prev:string;
 nombreimagen ='default.jpg';

  constructor(
    private productoService: ProductoService,
    private activatedRoute: ActivatedRoute,
    private toastr: ToastrService,
    private router: Router,
    private areaService:AreaService,
    private tipoProductoService:TipoProductoService,
    private uploadService:ImagenService,
    private sanitizer:DomSanitizer
  ) { }

  ngOnInit() {
    const id = this.activatedRoute.snapshot.params.id;
    this.productoService.detail(id).subscribe(
      data => {
        this.producto = data;
        this.prev = "assets/img"+this.producto.imagen ; 
        this.filename=this.producto.imagen;
        this.actualizar();
      },
      err => {
        this.toastr.error(err.error.mensaje, 'Fail', {
          timeOut: 3000,  positionClass: 'toast-top-center',
        });
        this.router.navigate(['/']);
      }
    );
    
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
    );
   
  }


  actualizar():void{
    
    this.areaService.detalle(this.producto.areaid).subscribe(
      data=>{
        this.areat=data.nombre;
        this.areaid = this.producto.areaid;
        console.log( this.areaid);
      },
      err =>{
        console.log("error 2 : ",err);
      }
    );
    this.tipoProductoService.detalle(this.producto.tipoproductoid).subscribe(
      data=>{
        this.tipot=data.nombre;
        this.tipoproductoid = this.producto.tipoproductoid;
        console.log( this.tipoproductoid);
      },
      err =>{
        console.log("error 2 : ",err);
      }
    )
  }
  onUpdate(): void {
    this.producto.imagen=this.filename;
    const id = this.activatedRoute.snapshot.params.id;
    this.productoService.update(id, this.producto).subscribe(
      data => {
       this.toastr.success('Producto Actualizado', 'OK', {
          timeOut: 3000, positionClass: 'toast-top-center'
          
          
        });
        console.log("respuesta",data);
        console.log("tipo :", this.producto.tipoproductoid);
       

        console.log( "area :",this.producto.tipoproductoid);
        

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
    if(this.SelectFiles !=undefined){
      this.uploadService.upload(this.SelectFiles[0]).subscribe(
        data => {
          console.log(data);
         
         
         if(data != ""){
          
          this.onUpdate();
         }
         
        },
        err => {
          console.log(err);
          this.toastr.error(err.error.mensaje, 'Seleccione una imagen o imagen muy grande' , {
            timeOut: 3000,  positionClass: 'toast-top-center',
          });
          // this.router.navigate(['/']);
        }
        
  
      );
    }else{
      this.onUpdate();
    }
    
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
