package com.example.proyectonativas.modelos;

public class ItemPedido {
    private Integer id;
    private Pedido pedido;
    private Producto producto;
    private int cantidad;

    public ItemPedido(){

    }

    public ItemPedido(Pedido pedido, Producto producto, int cantidad) {
        this.pedido = pedido;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
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
}
