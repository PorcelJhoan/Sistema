package com.tutorial.crud.controller;

import com.tutorial.crud.dto.AreaDto;
import com.tutorial.crud.dto.Mensaje;
import com.tutorial.crud.dto.TipoProductoDto;
import com.tutorial.crud.entity.TipoProducto;
import com.tutorial.crud.repository.AreaRepository;
import com.tutorial.crud.repository.TipoProductoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipoproducto")
@CrossOrigin(origins = "*")
public class TipoProductoController {
    @Autowired
    TipoProductoRepository tipoProductoRepository;

    @GetMapping("/lista")
    public ResponseEntity<List<TipoProducto>> list(){
        List<TipoProducto> list = tipoProductoRepository.findAll();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<TipoProducto> getById(@PathVariable("id") int id){

        Optional<TipoProducto> area= tipoProductoRepository.findById(id);
        return new ResponseEntity(area, HttpStatus.OK);
    }

    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<TipoProducto> getByNombre(@PathVariable("nombre") String nombre){
        if(!tipoProductoRepository.existsByNombre(nombre))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Optional<TipoProducto> area = tipoProductoRepository.findByNombre(nombre);
        return new ResponseEntity(area, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody TipoProductoDto areaDto){
        if(StringUtils.isBlank(areaDto.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);

        if(tipoProductoRepository.existsByNombre(areaDto.getNombre()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        TipoProducto area = new TipoProducto(areaDto.getNombre());
        tipoProductoRepository.save(area);
        return new ResponseEntity(new Mensaje("producto creado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody TipoProductoDto areaDto) {
        if (!tipoProductoRepository.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        if (tipoProductoRepository.existsByNombre(areaDto.getNombre()) && tipoProductoRepository.findByNombre(areaDto.getNombre()).get().getId() != id)
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(areaDto.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);


        TipoProducto area =new TipoProducto(areaDto.getId(),areaDto.getNombre());


        tipoProductoRepository.save(area);
        return new ResponseEntity(new Mensaje("producto actualizado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if(!tipoProductoRepository.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Optional<TipoProducto> area= tipoProductoRepository.findById(id);
        tipoProductoRepository.deleteById(id);
        return new ResponseEntity(new Mensaje("producto eliminado"), HttpStatus.OK);
    }
}
