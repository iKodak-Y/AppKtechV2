package com.ktech.appktechv2.controlador;

import com.ktech.appktechv2.SqlConnection;
import com.ktech.appktechv2.modelo.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class CargaMasivaProductosController implements Initializable {
    @FXML
    private TextField rutaArchivo;
    @FXML
    private TableView<ResultadoCarga> tablaResultados;
    @FXML
    private TableColumn<ResultadoCarga, String> columnaProducto;
    @FXML
    private TableColumn<ResultadoCarga, String> columnaEstado;
    @FXML
    private TableColumn<ResultadoCarga, String> columnaError;

    private File archivoSeleccionado;
    @FXML
    private Button btnPlantillas;
    @FXML
    private CheckBox chkForzarSobrescritura;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnaProducto.setCellValueFactory(cellData -> cellData.getValue().productoProperty());
        columnaEstado.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());
        columnaError.setCellValueFactory(cellData -> cellData.getValue().errorProperty());
    }

    @FXML
    private void seleccionarArchivo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos Excel", "*.xlsx"),
                new FileChooser.ExtensionFilter("Archivos CSV", "*.csv")
        );
        archivoSeleccionado = fileChooser.showOpenDialog(null);
        if (archivoSeleccionado != null) {
            rutaArchivo.setText(archivoSeleccionado.getAbsolutePath());
        }
    }

    @FXML
    private void cargarProductos(ActionEvent event) {
        if (archivoSeleccionado == null) {
            mostrarAlerta("Advertencia", "Debe seleccionar un archivo primero.", Alert.AlertType.WARNING);
            return;
        }
        List<Producto> productos = new ArrayList<>();
        ObservableList<ResultadoCarga> resultados = FXCollections.observableArrayList();
        try {
            if (archivoSeleccionado.getName().endsWith(".xlsx")) {
                procesarExcel(productos, resultados);
            } else if (archivoSeleccionado.getName().endsWith(".csv")) {
                procesarCSV(productos, resultados);
            } else {
                mostrarAlerta("Error", "Formato de archivo no soportado. Use .xlsx o .csv.", Alert.AlertType.ERROR);
                return;
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al procesar el archivo: " + e.getMessage(), Alert.AlertType.ERROR);
        }
        tablaResultados.setItems(resultados);
    }

    private void procesarExcel(List<Producto> productos, ObservableList<ResultadoCarga> resultados) throws Exception {
        try (FileInputStream fis = new FileInputStream(archivoSeleccionado);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Saltar encabezados

                Producto producto = new Producto();
                ResultadoCarga resultado = new ResultadoCarga();

                try {
                    String codigo = getCellValue(row.getCell(0));
                    String nombre = getCellValue(row.getCell(1));
                    double precio = Double.parseDouble(getCellValue(row.getCell(2)));
                    double pvp = Double.parseDouble(getCellValue(row.getCell(3)));
                    int stock = (int) Math.round(Double.parseDouble(getCellValue(row.getCell(4))));
                    double iva = Double.parseDouble(getCellValue(row.getCell(5)));
                    String categoria = getCellValue(row.getCell(6));

                    producto.setCodigo(codigo);
                    producto.setNombre(nombre);
                    producto.setPrecio(precio);
                    producto.setPvp(pvp);
                    producto.setStockActual(stock);
                    producto.setIva(iva);
                    producto.setNombreCategoria(categoria);

                    resultado.setProducto(codigo);
                    resultado.setNombre(nombre);
                    resultado.setPrecio(precio);
                    resultado.setPvp(pvp);
                    resultado.setStock(stock);
                    resultado.setIva(iva);
                    resultado.setCategoria(categoria);
                    resultado.setEstado("Éxito");

                    productos.add(producto);
                } catch (NumberFormatException e) {
                    resultado.setProducto(getCellValue(row.getCell(0)));
                    resultado.setEstado("Error");
                    resultado.setError("Formato inválido en una celda: " + e.getMessage());
                } catch (Exception e) {
                    resultado.setProducto(getCellValue(row.getCell(0)));
                    resultado.setEstado("Error");
                    resultado.setError(e.getMessage());
                }

                resultados.add(resultado);
            }
        }
    }

    private void procesarCSV(List<Producto> productos, ObservableList<ResultadoCarga> resultados) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(archivoSeleccionado))) {
            String linea;
            boolean primeraLinea = true; // Para saltar encabezados

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] valores = linea.split(",");
                Producto producto = new Producto();
                ResultadoCarga resultado = new ResultadoCarga();

                try {
                    String codigo = valores[0];
                    String nombre = valores[1];
                    double precio = Double.parseDouble(valores[2]);
                    double pvp = Double.parseDouble(valores[3]);
                    int stock = Integer.parseInt(valores[4]);
                    double iva = Double.parseDouble(valores[5]);
                    String categoria = valores[6];

                    producto.setCodigo(codigo);
                    producto.setNombre(nombre);
                    producto.setPrecio(precio);
                    producto.setPvp(pvp);
                    producto.setStockActual(stock);
                    producto.setIva(iva);
                    producto.setNombreCategoria(categoria);

                    resultado.setProducto(codigo);
                    resultado.setNombre(nombre);
                    resultado.setPrecio(precio);
                    resultado.setPvp(pvp);
                    resultado.setStock(stock);
                    resultado.setIva(iva);
                    resultado.setCategoria(categoria);
                    resultado.setEstado("Éxito");

                    productos.add(producto);
                } catch (NumberFormatException e) {
                    resultado.setProducto(valores[0]);
                    resultado.setEstado("Error");
                    resultado.setError("Formato inválido en una celda: " + e.getMessage());
                } catch (Exception e) {
                    resultado.setProducto(valores[0]);
                    resultado.setEstado("Error");
                    resultado.setError(e.getMessage());
                }

                resultados.add(resultado);
            }
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            default:
                return "";
        }
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    @FXML
    public void guardarBaseDatos(ActionEvent actionEvent) {
        ObservableList<ResultadoCarga> resultados = tablaResultados.getItems();

        if (resultados.isEmpty()) {
            mostrarAlerta("Advertencia", "Debe cargar los productos en la tabla antes de guardar.", Alert.AlertType.WARNING);
            return;
        }

        ProductoDAO productoDAO = new ProductoDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        int totalProcesados = 0, totalErrores = 0, totalActualizados = 0, totalNuevos = 0;

        try (Connection conn = new SqlConnection().getConexion()) {
            conn.setAutoCommit(false); // Iniciar transacción

            for (ResultadoCarga resultado : resultados) {
                if ("Error".equals(resultado.getEstado())) {
                    totalErrores++;
                    continue;
                }

                try {
                    totalProcesados++;

                    // Buscar si el producto ya existe
                    Producto productoExistente = productoDAO.buscarPorCodigo(resultado.getProducto());

                    // Construir el producto con los datos del resultado
                    Producto producto = construirProducto(resultado);

                    // Buscar la categoría por nombre
                    Categoria categoria = categoriaDAO.obtenerPorNombre(resultado.getCategoria());
                    if (categoria == null) {
                        marcarError(resultado, "Categoría no encontrada: " + resultado.getCategoria());
                        totalErrores++;
                        continue;
                    }
                    producto.setCategoria(categoria);

                    if (productoExistente != null) {
                        if (chkForzarSobrescritura.isSelected()) {
                            // Actualizar producto existente
                            producto.setId(productoExistente.getId());
                            if (productoDAO.actualizar(producto)) {
                                resultado.setEstado("Actualizado ✅");
                                totalActualizados++;
                            } else {
                                marcarError(resultado, "Error al actualizar el producto");
                                totalErrores++;
                            }
                        } else {
                            marcarError(resultado, "El producto ya existe y no se permite sobrescribir");
                            totalErrores++;
                        }
                    } else {
                        // Insertar nuevo producto
                        if (productoDAO.guardar(producto)) {
                            resultado.setEstado("Insertado ✅");
                            totalNuevos++;
                        } else {
                            marcarError(resultado, "Error al insertar el producto");
                            totalErrores++;
                        }
                    }
                } catch (Exception e) {
                    marcarError(resultado, "Error: " + e.getMessage());
                    totalErrores++;
                    registrarError(e, "guardarBaseDatos");
                }
            }

            if (totalErrores == 0) {
                conn.commit();
            } else {
                conn.rollback();
                mostrarAlerta("Advertencia", "Se encontraron errores durante la carga. Los cambios no se guardaron.", Alert.AlertType.WARNING);
            }

        } catch (SQLException ex) {
            mostrarAlerta("Error", "Error de conexión: " + ex.getMessage(), Alert.AlertType.ERROR);
            totalErrores++;
        }

        tablaResultados.refresh();
        mostrarResumen(totalProcesados, totalNuevos, totalActualizados, totalErrores);
    }

    private void marcarError(ResultadoCarga resultado, String mensaje) {
        resultado.setError(mensaje);
        resultado.setEstado("Error");
        resultado.setIcono("❌"); // Icono de error
    }

    private void mostrarResumen(int procesados, int nuevos, int actualizados, int errores) {
        String mensaje = String.format("""
                Resumen de la operación:
                • Total procesados: %d
                • Nuevos productos: %d
                • Actualizados: %d
                • Errores: %d
                """, procesados, nuevos, actualizados, errores);

        Alert.AlertType tipo = errores > 0 ? Alert.AlertType.WARNING : Alert.AlertType.INFORMATION;
        mostrarAlerta("Resultado de la carga", mensaje, tipo);
    }

    private Producto construirProducto(ResultadoCarga resultado) {
        Producto producto = new Producto();
        producto.setCodigo(resultado.getProducto());
        producto.setNombre(resultado.getNombre());
        producto.setPrecio(resultado.getPrecio());
        producto.setPvp(resultado.getPvp());
        producto.setStockActual(resultado.getStock());
        producto.setIva(resultado.getIva());
        producto.setNombreCategoria(resultado.getCategoria());
        producto.setEstado("A");
        producto.setStockInicial(resultado.getStock());
        return producto;
    }

    private void registrarError(Exception e, String procedimiento) {
        try (Connection conn = new SqlConnection().getConexion()) {
            String logErrorQuery = "INSERT INTO LogErrores (ErrorNumber, ErrorSeverity, ErrorState, ErrorProcedure, ErrorLine, ErrorMessage, FechaError) VALUES (?, ?, ?, ?, ?, ?, GETDATE())";
            PreparedStatement logStmt = conn.prepareStatement(logErrorQuery);

            logStmt.setInt(1, 0); // Número de error (ajusta según sea necesario)
            logStmt.setInt(2, 0); // Severidad del error
            logStmt.setInt(3, 0); // Estado del error
            logStmt.setString(4, procedimiento); // Procedimiento donde ocurrió el error
            logStmt.setInt(5, 0); // Línea del error
            logStmt.setString(6, e.getMessage()); // Mensaje de error
            logStmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Error al registrar el error en la base de datos: " + ex.getMessage());
        }
    }

    @FXML
    public void descargarPlantillas(ActionEvent actionEvent) {
        try {
            // Crear un DirectoryChooser para que el usuario seleccione la carpeta de destino
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Seleccionar Carpeta de Descarga");
            File directorioSeleccionado = directoryChooser.showDialog(null);

            if (directorioSeleccionado == null) {
                mostrarAlerta("Advertencia", "Debe seleccionar una carpeta para continuar.", Alert.AlertType.WARNING);
                return;
            }

            // Ruta completa donde se guardarán las plantillas
            String rutaDirectorio = directorioSeleccionado.getAbsolutePath();

            // Crear el archivo Excel
            File excelFile = new File(rutaDirectorio + "/plantilla_productos.xlsx");
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Productos");
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Código", "Descripción", "Precio_Compra", "PVP", "Stock", "IVA", "Categoría"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            try (FileOutputStream fos = new FileOutputStream(excelFile)) {
                workbook.write(fos);
            }
            workbook.close();

            // Crear el archivo CSV
            File csvFile = new File(rutaDirectorio + "/plantilla_productos.csv");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
                writer.write("Código,Descripción,Precio_Compra,PVP,Stock,IVA,Categoría");
            }

            mostrarAlerta("Éxito", "Las plantillas han sido descargadas correctamente en:\n" + rutaDirectorio, Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudieron descargar las plantillas: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}