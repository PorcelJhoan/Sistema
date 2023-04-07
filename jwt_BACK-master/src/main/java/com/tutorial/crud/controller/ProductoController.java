package com.tutorial.crud.controller;

import com.tutorial.crud.dto.Mensaje;
import com.tutorial.crud.dto.ProductoDto;

import com.tutorial.crud.entity.Imagen;
import com.tutorial.crud.entity.Producto;
import com.tutorial.crud.service.ProductoService;
import com.tutorial.crud.service.UploadFileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/producto")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @Autowired
    private UploadFileService uploadFileService;


    @GetMapping("/lista")
    public ResponseEntity<Page<Producto>> list(@RequestParam (defaultValue ="0" ) int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "nombre") String order,@RequestParam(defaultValue = "true") boolean asc ){
        Page<Producto> list = productoService.paginas(
                PageRequest.of(page,size, Sort.by(order))
        );
        if(!asc){
            list =  productoService.paginas(
                    PageRequest.of(page,size, Sort.by(order).descending()));
        }
        return new ResponseEntity<Page<Producto>>(list, HttpStatus.OK);
    }


    @GetMapping("/detail/{id}")
    public ResponseEntity<Producto> getById(@PathVariable("id") int id){
        if(!productoService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Producto producto = productoService.getOne(id).get();
        return new ResponseEntity(producto, HttpStatus.OK);
    }

    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Producto> getByNombre(@PathVariable("nombre") String nombre){
        if(!productoService.existsByNombre(nombre))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Producto producto = productoService.getByNombre(nombre).get();
        return new ResponseEntity(producto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProductoDto productoDto) throws IOException {


        if(StringUtils.isBlank(productoDto.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(productoDto.getPrecio()==null || productoDto.getPrecio()<0 )
            return new ResponseEntity(new Mensaje("el precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);
        if(productoService.existsByNombre(productoDto.getNombre()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
       /* if(!imagen.isEmpty()){
            Path directorioImagenes = Paths.get("src//main//resources/images");
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
            byte[] bytesImge = imagen.getBytes();
            Path rutaCompleta = Paths.get(rutaAbsoluta+"//"+imagen.getOriginalFilename());
            Files.write(rutaCompleta,bytesImge);

        }*/
        System.out.println("nombre create : "+ productoDto.getImagen());

        Producto producto = new Producto(productoDto.getNombre(), productoDto.getPrecio(),productoDto.getCaracteristica(),productoDto.getCantidad(),productoDto.getMedida(),productoDto.getAreaid(),productoDto.getTipoproductoid(),productoDto.getImagen());
        productoService.save(producto);
        return new ResponseEntity(new Mensaje("producto creado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/imagen")
    public ResponseEntity<Imagen> imagen(@RequestParam("img") MultipartFile file) throws IOException {


        //imagen
        String nombreimagen = uploadFileService.saveImage(file);
        System.out.println("nombre : "+nombreimagen);
        Imagen im= new Imagen();
        im.setImagen(nombreimagen);


        return new ResponseEntity(im, HttpStatus.OK);
    }
/*@GetMapping("/lista")
    public ResponseEntity<List<Producto>> list(){
        List<Producto> list = productoService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }*/

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody ProductoDto productoDto){
        System.out.println("\nproducto : \n"+productoDto.getTipoproductoid());
        if(!productoService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        if(productoService.existsByNombre(productoDto.getNombre()) && productoService.getByNombre(productoDto.getNombre()).get().getId() != id)
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(productoDto.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(productoDto.getPrecio()==null || productoDto.getPrecio()<0 )
            return new ResponseEntity(new Mensaje("el precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);

        Producto producto = productoService.getOne(id).get();
        producto.setNombre(productoDto.getNombre());
        producto.setPrecio(productoDto.getPrecio());
        producto.setCantidad(productoDto.getCantidad());
        producto.setCaracteristica(productoDto.getCaracteristica());
        producto.setMedida(productoDto.getMedida());
        producto.setAreaid(productoDto.getAreaid());
        producto.setTipoproductoid(productoDto.getTipoproductoid());
        producto.setImagen((productoDto.getImagen()));
        System.out.println("tipo : "+producto.getTipoproductoid());
        productoService.save(producto);
        return new ResponseEntity(new Mensaje("producto actualizado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if(!productoService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        productoService.delete(id);
        return new ResponseEntity(new Mensaje("producto eliminado"), HttpStatus.OK);
    }


}
