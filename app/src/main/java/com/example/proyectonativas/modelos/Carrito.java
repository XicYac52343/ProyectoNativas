package com.example.proyectonativas.modelos;

import java.util.List;

public class Carrito {
    private int id;

    private List<Item> item;

    private Usuario usuario;

    private float totalCarrito = 0;

    public Carrito(){

    }
    public Carrito(List<Item> item, Usuario usuario, float totalCarrito) {
        this.item = item;
        this.usuario = usuario;
        this.totalCarrito = totalCarrito;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public float getTotalCarrito() {
        return totalCarrito;
    }

    public void setTotalCarrito(float totalCarrito) {
        this.totalCarrito = totalCarrito;
    }
}

