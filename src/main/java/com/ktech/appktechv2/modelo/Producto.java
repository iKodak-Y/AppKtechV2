package com.ktech.appktechv2.modelo;

public class Producto {

    private String NombreCategoria;
    private int id;
    private String codigo;
    private String nombre;
    private double precio; // Precio de compra
    private double pvp; // Precio de venta al público
    private int stockInicial;
    private int stockActual;
    private double iva;
    private String estado;
    private int idCategoria;
    private int cantidad;
    private Categoria categoria;
    private double subtotal; // nuevo para factura
    private double total; // nuevo para factura
    private String nombreCategoria;


    public Producto() {
    }

    public Producto(int IDProducto, String Codigo, String Nombre, double Precio,
                    double PVP, int StockActual, double IVA, String Estado,
                    String NombreCategoria) {
        this.id = IDProducto;
        this.codigo = Codigo;
        this.nombre = Nombre;
        this.precio = Precio;
        this.pvp = PVP;
        this.stockActual = StockActual;
        this.iva = IVA;
        this.estado = Estado;
        this.NombreCategoria = NombreCategoria;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPvp() {
        return pvp;
    }

    public int getStockInicial() {
        return stockInicial;
    }

    public void setStockInicial(int stockInicial) {
        this.stockInicial = stockInicial;
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
        calcularTotal();
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * // Método para calcular el total basado en la cantidad y el pvp public
     * double getTotal() { return Math.round(cantidad * pvp * 100.0) / 100.0; }
     * <p>
     * // Método para calcular el subtotal sin IVA public double getSubtotal()
     * { return Math.round(cantidad * pvp * 100.0) / 100.0; // Usar pvp para el
     * subtotal también }
     * <p>
     * // Método para calcular el precio total con IVA incluido public void
     * calcularTotal() { this.pvp = Math.round(precio * (1 + iva) * 100.0) /
     * 100.0; }
     *
     * @Override public String toString() { return codigo + " - " + nombre; }
     */
    //metos nuevos para factura
    public double getTotal() {
        double subtotal = getSubtotal();
        return subtotal + (subtotal * (iva));  // iva ya viene como decimal (0.15 = 15%)
    }

    public void setPvp(double pvp) {
        this.pvp = pvp;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // Método para calcular el subtotal sin IVA
    public double getSubtotal() {
        // El subtotal es simplemente cantidad * pvp, sin IVA
        return cantidad * pvp;
    }

    // Método para calcular todos los valores
    public void calcularTotal() {
        this.subtotal = cantidad * pvp;
        this.total = subtotal + (subtotal * (iva / 100));
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }

    public String getNombreCategoria() {
        return NombreCategoria;
    }

    public void setNombreCategoria(String NombreCategoria) {
        this.NombreCategoria = NombreCategoria;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
        if (categoria != null) {
            this.idCategoria = categoria.getId();
            this.NombreCategoria = categoria.getNombre();
        }
    }
}
