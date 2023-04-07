package com.tutorial.crud.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private float precio;
    private String caracteristica;

    private Integer cantidad;

    private String medida;

    private Integer areaid;

    private Integer tipoproductoid;
    private String imagen;

    public Producto() {
    }



    public String getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }

    public Integer getTipoproductoid() {
        return tipoproductoid;
    }

    public void setTipoproductoid(Integer tipoproductoid) {
        this.tipoproductoid = tipoproductoid;
    }

    public Producto(String nombre, float precio, String caracteristica, Integer cantidad, String medida, Integer area_id, Integer tipo_producto_id,String imagen) {

        this.nombre = nombre;
        this.precio = precio;
        this.caracteristica = caracteristica;
        this.cantidad = cantidad;
        this.medida = medida;
        this.areaid = area_id;
        this.tipoproductoid = tipo_producto_id;
        this.imagen=imagen;
    }



    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
}
