package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Area;
import com.tutorial.crud.entity.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoProductoRepository extends JpaRepository<TipoProducto, Integer> {
    Optional<TipoProducto> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}