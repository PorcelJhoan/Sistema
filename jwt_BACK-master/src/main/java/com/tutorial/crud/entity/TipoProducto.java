package com.tutorial.crud.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_producto")
public class TipoProducto {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", length = 455)
    private String nombre;

    public TipoProducto(Integer id, String nombre) {
        this.id=id;
        this.nombre=nombre;
    }
    public TipoProducto( String nombre) {
        this.nombre=nombre;
    }

    public TipoProducto() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}