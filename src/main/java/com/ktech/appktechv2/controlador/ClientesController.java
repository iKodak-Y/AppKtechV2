package com.ktech.appktechv2.controlador;

import com.ktech.appktechv2.modelo.Cliente;
import com.ktech.appktechv2.modelo.ClienteDAO;
import com.ktech.appktechv2.util.ValidadorDocumento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClientesController implements Initializable {

    @FXML
    private TextField txt_buscar_cliente;
    @FXML
    private Button btn_buscar;
    @FXML
    private Button btn_limpiar;
    @FXML
    private TextField txt_nombres;
    @FXML
    private TextField txt_apellidos;
    @FXML
    private TextField txt_cedula;
    @FXML
    private ComboBox<String> cmb_tipo_documento;
    @FXML
    private TextField txt_direccion;
    @FXML
    private TextField txt_telefono;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_sexo;
    @FXML
    private Button btn_guardar;
    @FXML
    private Button btn_eliminar;
    private Button btn_cerrar;
    @FXML
    private TableView<Cliente> tbl_clientes;
    @FXML
    private TableColumn<Cliente, String> col_nombres;
    @FXML
    private TableColumn<Cliente, String> col_apellidos;
    @FXML
    private TableColumn<Cliente, String> col_cedula;
    @FXML
    private TableColumn<Cliente, String> col_direccion;
    @FXML
    private TableColumn<Cliente, String> col_telefono;
    @FXML
    private TableColumn<Cliente, String> col_email;
    @FXML
    private TableColumn<Cliente, String> col_sexo;

    private ClienteDAO clienteDAO = new ClienteDAO();
    private ObservableList<Cliente> listaClientes;
    private Cliente clienteSeleccionado;

    private Cliente clienteRegistrado;

    public Cliente obtenerClienteSeleccionado() {
        return clienteRegistrado;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        col_nombres.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col_apellidos.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        col_cedula.setCellValueFactory(new PropertyValueFactory<>("cedulaRUC"));
        col_direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        col_telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_sexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));

        cargarClientes();
        tbl_clientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                mostrarCliente(newSelection);
                clienteSeleccionado = newSelection;
            }
        });

        cmb_tipo_documento.setItems(FXCollections.observableArrayList("Cedula", "RUC"));
    }

    private void cargarClientes() {
        List<Cliente> clientes = clienteDAO.obtenerTodos();
        listaClientes = FXCollections.observableArrayList(clientes);
        tbl_clientes.setItems(listaClientes);
    }

    private void mostrarCliente(Cliente cliente) {
        if (cliente != null) {
            txt_nombres.setText(cliente.getNombre());
            txt_apellidos.setText(cliente.getApellido());
            txt_cedula.setText(cliente.getCedulaRUC());
            txt_direccion.setText(cliente.getDireccion());
            txt_telefono.setText(cliente.getTelefono());
            txt_email.setText(cliente.getEmail());
            txt_sexo.setText(String.valueOf(cliente.getSexo()));
            cmb_tipo_documento.setValue(cliente.getCedulaRUC().length() == 10 ? "Cedula" : "RUC");
        }
    }

    private void limpiarCampos() {
        txt_nombres.clear();
        txt_apellidos.clear();
        txt_cedula.clear();
        txt_direccion.clear();
        txt_telefono.clear();
        txt_email.clear();
        txt_sexo.clear();
        cmb_tipo_documento.getSelectionModel().clearSelection();
        clienteSeleccionado = null;
    }

    @FXML
    private void acc_guardar(ActionEvent event) {
        String nombre = txt_nombres.getText();
        String apellido = txt_apellidos.getText();
        String cedula = txt_cedula.getText();
        String tipoDocumento = cmb_tipo_documento.getValue();
        String direccion = txt_direccion.getText();
        String telefono = txt_telefono.getText();
        String email = txt_email.getText();
        String sexo = txt_sexo.getText();

        if (ValidadorDocumento.esCampoVacio(nombre) || ValidadorDocumento.esCampoVacio(apellido)
                || ValidadorDocumento.esCampoVacio(cedula) || ValidadorDocumento.esCampoVacio(tipoDocumento)
                || ValidadorDocumento.esCampoVacio(direccion) || ValidadorDocumento.esCampoVacio(telefono)
                || ValidadorDocumento.esCampoVacio(email) || ValidadorDocumento.esCampoVacio(sexo)) {
            mostrarAlerta("Error de Validación", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
            return;
        }

        if (tipoDocumento.equals("Cedula") && !ValidadorDocumento.validarCedula(cedula)) {
            mostrarAlerta("Documento inválido", "La cédula ingresada no es válida.", Alert.AlertType.ERROR);
            return;
        }

        if (tipoDocumento.equals("RUC") && !ValidadorDocumento.validarRUC(cedula)) {
            mostrarAlerta("Documento inválido", "El RUC ingresado no es válido.", Alert.AlertType.ERROR);
            return;
        }

        Cliente clienteExistente = clienteDAO.buscarPorCedulaONombre(cedula);
        if (clienteExistente != null && (clienteSeleccionado == null || clienteExistente.getIdCliente() != clienteSeleccionado.getIdCliente())) {
            mostrarAlerta("Documento duplicado", "El número de " + tipoDocumento + " ya está registrado.", Alert.AlertType.ERROR);
            return;
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setCedulaRUC(cedula);
        cliente.setDireccion(direccion);
        cliente.setTelefono(telefono);
        cliente.setEmail(email);
        cliente.setSexo(sexo.charAt(0));
        cliente.setEstado('A');

        boolean exito;
        if (clienteSeleccionado == null) {
            exito = clienteDAO.guardar(cliente);
        } else {
            cliente.setIdCliente(clienteSeleccionado.getIdCliente());
            exito = clienteDAO.actualizar(cliente);
        }

        if (exito) {
            mostrarAlerta("Éxito", "Los datos del cliente han sido guardados correctamente.", Alert.AlertType.INFORMATION);

            // Guardar el cliente registrado para que pueda ser recuperado por el TicketsVentasController
            clienteRegistrado = cliente;

            // Cerrar la ventana emergente
            Stage stage = (Stage) btn_guardar.getScene().getWindow();
            stage.close();
        } else {
            mostrarAlerta("Error", "Ocurrió un error al guardar los datos del cliente.", Alert.AlertType.ERROR);
        }

        cargarClientes();
        limpiarCampos();
    }

    @FXML
    private void acc_eliminar(ActionEvent event) {
        Cliente clienteSeleccionado = tbl_clientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null) {
            boolean exito = clienteDAO.eliminar(clienteSeleccionado.getIdCliente());
            if (exito) {
                mostrarAlerta("Éxito", "El cliente ha sido eliminado correctamente.", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "Ocurrió un error al eliminar el cliente.", Alert.AlertType.ERROR);
            }
            cargarClientes();
            limpiarCampos();
        } else {
            mostrarAlerta("Error", "Debe seleccionar un cliente para eliminar.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void acc_buscar(ActionEvent event) {
        String termino = txt_buscar_cliente.getText();
        if (termino.isEmpty()) {
            cargarClientes();
        } else {
            Cliente cliente = clienteDAO.buscarPorCedulaONombre(termino);
            if (cliente != null) {
                listaClientes = FXCollections.observableArrayList(cliente);
                tbl_clientes.setItems(listaClientes);
            } else {
                tbl_clientes.getItems().clear();
                mostrarAlerta("No encontrado", "No se encontró ningún cliente con el término ingresado.", Alert.AlertType.WARNING);
            }
        }
    }

    @FXML
    private void acc_limpiar(ActionEvent event) {
        limpiarCampos();
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
