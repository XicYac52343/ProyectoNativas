package com.example.proyectonativas.modelos;

import java.util.List;

public class Producto {
    private Integer id;
    private String nombre;
    private String categoria;
    private String descripcion;
    private int stock;
    private float precio;
    private int cantidadDescuento;

    private List<Usuario> usuariosFavoritos;    

    public Producto(){

    }

    public Producto(String nombre, String categoria, String descripcion, int stock, float precio, int cantidadDescuento, List<Usuario> usuariosFavoritos) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.stock = stock;
        this.precio = precio;
        this.cantidadDescuento = cantidadDescuento;
        this.usuariosFavoritos = usuariosFavoritos;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getCantidadDescuento() {
        return cantidadDescuento;
    }

    public void setCantidadDescuento(int cantidadDescuento) {
        this.cantidadDescuento = cantidadDescuento;
    }

    public List<Usuario> getUsuariosFavoritos() {
        return usuariosFavoritos;
    }

    public void setUsuariosFavoritos(List<Usuario> usuariosFavoritos) {
        this.usuariosFavoritos = usuariosFavoritos;
    }
}
