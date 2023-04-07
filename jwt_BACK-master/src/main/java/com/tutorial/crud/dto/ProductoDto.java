package com.tutorial.crud.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

public class ProductoDto {

    @NotBlank
    private String nombre;
    @Min(0)
    private Float precio;
    @Null
    private String caracteristica;
    @Min(0)
    private Integer cantidad;
    @NotBlank
    private String medida;
    @Min(0)
    private Integer areaid;
    @Min(0)
    private Integer tipoproductoid;
    @NotBlank
    private String imagen;

    public ProductoDto() {
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

    public ProductoDto(String nombre, Float precio, String caracteristica, Integer cantidad, String medida, Integer area_id, Integer tipoproducto_id,String imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.caracteristica = caracteristica;
        this.cantidad = cantidad;
        this.medida = medida;
        this.areaid = area_id;
        this.tipoproductoid = tipoproducto_id;
        this.imagen =imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }



    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
