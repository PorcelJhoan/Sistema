package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Area;
import com.tutorial.crud.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AreaRepository extends JpaRepository<Area, Integer> {
    Optional<Area> findByNombre(String nombre);
    boolean existsByNombre(String nombre);



}