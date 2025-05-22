package com.example.proyectonativas.modelos;

import java.time.LocalDate;
import java.util.List;

public class Pedido {
    private Integer id;

    private String fecha;
    private float total;
    private float precioEnvio;
    private String estado;

    private Usuario usuario;


    private List<ItemPedido> listaItems;

    public Pedido(){

    }

    public Pedido(String fecha, float total, float precioEnvio, String estado, Usuario usuario, List<ItemPedido> listaItems) {
        this.fecha = fecha;
        this.total = total;
        this.precioEnvio = precioEnvio;
        this.estado = estado;
        this.usuario = usuario;
        this.listaItems = listaItems;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getPrecioEnvio() {
        return precioEnvio;
    }

    public void setPrecioEnvio(float precioEnvio) {
        this.precioEnvio = precioEnvio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemPedido> getListaItems() {
        return listaItems;
    }

    public void setListaItems(List<ItemPedido> listaItems) {
        this.listaItems = listaItems;
    }
}
