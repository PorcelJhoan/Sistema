package com.tutorial.crud.repository;

import com.tutorial.crud.entity.VentaHasProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaHasProductoRepository extends JpaRepository<VentaHasProducto, Integer> {
}