package com.example.proyectonativas.modelos;

public class Item {
    private Integer id;

    private Carrito carrito;

    private Producto producto;

    private int cantidad;

    public Item(){

    }
    public Item(Carrito carrito, Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.carrito = carrito;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }
}
