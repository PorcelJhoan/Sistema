package com.tutorial.crud.dto;

import java.io.Serializable;
import java.util.Objects;

public class VentaHasProductoDto implements Serializable {
    private final Integer id;
    private final Integer venta;
    private final int producto;
    private final Integer cantidad;

    public VentaHasProductoDto(Integer id, Integer venta, int producto, Integer cantidad) {
        this.id = id;
        this.venta = venta;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Integer getId() {
        return id;
    }

    public Integer getVenta() {
        return venta;
    }

    public int getProducto() {
        return producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VentaHasProductoDto entity = (VentaHasProductoDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.venta, entity.venta) &&
                Objects.equals(this.producto, entity.producto) &&
                Objects.equals(this.cantidad, entity.cantidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, venta, producto, cantidad);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "venta = " + venta + ", " +
                "producto = " + producto + ", " +
                "cantidad = " + cantidad + ")";
    }
}
