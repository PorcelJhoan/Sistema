package com.tutorial.crud.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class VentaDto implements Serializable {
    private final Integer id;
    private final LocalDate fecha;
    private final String cliente;
    private final Integer idUsuario;

    public VentaDto(Integer id, LocalDate fecha, String cliente, Integer idUsuario) {
        this.id = id;
        this.fecha = fecha;
        this.cliente = cliente;
        this.idUsuario = idUsuario;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VentaDto entity = (VentaDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.fecha, entity.fecha) &&
                Objects.equals(this.cliente, entity.cliente) &&
                Objects.equals(this.idUsuario, entity.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fecha, cliente, idUsuario);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "fecha = " + fecha + ", " +
                "cliente = " + cliente + ", " +
                "idUsuario = " + idUsuario + ")";
    }
}
