package com.tutorial.crud.entity;

import javax.persistence.*;

@Entity
@Table(name = "venta_has_producto")
public class VentaHasProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta_producto", nullable = false)
    private Integer id;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "venta_id", nullable = false)
    private Integer venta;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "producto_id", nullable = false)
    private int producto;

    @Column(name = "cantidad")
    private Integer cantidad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVenta() {
        return venta;
    }

    public void setVenta(Integer venta) {
        this.venta = venta;
    }

    public int getProducto() {
        return producto;
    }

    public void setProducto(int producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}