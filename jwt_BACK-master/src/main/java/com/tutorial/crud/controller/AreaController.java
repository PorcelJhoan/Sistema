package com.tutorial.crud.controller;

import com.tutorial.crud.dto.AreaDto;
import com.tutorial.crud.dto.Mensaje;


import com.tutorial.crud.entity.Area;
import com.tutorial.crud.repository.AreaRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/area")
@CrossOrigin(origins = "*")
public class AreaController {
    @Autowired
    AreaRepository areaRepository;

    @GetMapping("/lista")
    public ResponseEntity<List<Area>> list(){
        List<Area> list = areaRepository.findAll();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Area> getById(@PathVariable("id") int id){

       Optional<Area> area= areaRepository.findById(id);
        return new ResponseEntity(area, HttpStatus.OK);
    }

    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Area> getByNombre(@PathVariable("nombre") String nombre){
        if(!areaRepository.existsByNombre(nombre))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Optional<Area> area = areaRepository.findByNombre(nombre);
        return new ResponseEntity(area, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AreaDto areaDto){
        if(StringUtils.isBlank(areaDto.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);

        if(areaRepository.existsByNombre(areaDto.getNombre()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        Area area = new Area(areaDto.getNombre());
        areaRepository.save(area);
        return new ResponseEntity(new Mensaje("producto creado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody AreaDto areaDto) {
        if (!areaRepository.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        if (areaRepository.existsByNombre(areaDto.getNombre()) && areaRepository.findByNombre(areaDto.getNombre()).get().getId() != id)
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(areaDto.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);


        Area area =new Area(areaDto.getId(),areaDto.getNombre());
        

        areaRepository.save(area);
        return new ResponseEntity(new Mensaje("producto actualizado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if(!areaRepository.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Optional<Area> area= areaRepository.findById(id);
        areaRepository.deleteById(id);
        return new ResponseEntity(new Mensaje("producto eliminado"), HttpStatus.OK);
    }
}
