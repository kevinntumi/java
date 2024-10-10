/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.cliente;

/**
 *
 * @author PAIN
 */
   import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import edu.uem.sgh.dao.FuncionarioDAO;
import edu.uem.sgh.model.Funcionario;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javax.swing.JOptionPane;

public class TelaClienteController {

    @FXML
    private Button btnEfectuarReserva;

    @FXML
    private ComboBox<?> cbxTipoQuarto;

    @FXML
    private DatePicker dataCheckinReserva;

    @FXML
    private DatePicker dataCheckoutReserva;

    @FXML
    private DatePicker dataDeNascimento;

    @FXML
    private Tab tabCadastrar1;

    @FXML
    private Tab tabCadastrar11;

    @FXML
    private TextField txtBI;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEndereco;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtResponsavelReserva;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtUsername;

    public void cadastrarFuncionario(String nome, String bi, String telefone, String email, String endereco, Date dataNascimento, String username, String password) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setDocumentoIdentificacao(bi);
        funcionario.setTelefone(telefone);
        funcionario.setEmail(email);
        funcionario.setEndereco(endereco);

        funcionario.setUsername(username); // Adicionando username
        funcionario.setPassword(password); // Adicionando password
        funcionario.setStatus(1);
        LocalDate dataAtual = LocalDate.now();
        funcionario.setDataRegisto(Date.from(dataAtual.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        // Chamada ao método de cadastro
        new FuncionarioDAO().cadastrarFuncionario(funcionario);

        // Mensagem de sucesso
    }

    @FXML
    void bloquearNum(KeyEvent event) {
        String bi = txtBI.getText();
        // Expressão regular: 12 números seguidos por uma letra
        String regex = "^\\d{12}[a-zA-Z]?$";

        // Se o texto não corresponde ao padrão, exibe uma mensagem ou sinaliza erro
        if (!bi.matches(regex)) {
            txtBI.setStyle("-fx-text-fill: red;"); // Muda o texto para vermelho
        } else {
            txtBI.setStyle("-fx-text-fill: black;"); // Volta ao normal se estiver correto
        }

        // Limitar o comprimento a 13 caracteres
        if (txtBI.getText().length() >= 13) {
            event.consume(); // Bloqueia mais caracteres após 13
        }
    }

    public void validarBIComRegex() {
        // Evento ao soltar a tecla
        txtBI.setOnKeyReleased(event -> {
            String bi = txtBI.getText();
            // Expressão regular: 12 números seguidos por uma letra
            String regex = "^\\d{12}[a-zA-Z]?$";

            // Verifica se o texto corresponde ao regex
            if (!bi.matches(regex)) {
                // Se não corresponder, muda a cor do texto para vermelho
                txtBI.setStyle("-fx-text-fill: red;");
            } else {
                // Se estiver correto, muda a cor do texto para preto
                txtBI.setStyle("-fx-text-fill: black;");
            }
        });

        // Evento ao digitar
        txtBI.setOnKeyTyped(event -> {
            // Limitar o comprimento a 13 caracteres
            if (txtBI.getText().length() >= 13) {
                event.consume(); // Bloqueia mais caracteres após 13
            }
        });
    }

    public void validarBIComLimite() {
        txtBI.setOnKeyTyped(event -> {
            String bi = txtBI.getText();
            char c = event.getCharacter().charAt(0);

            // Se já tem 12 caracteres, aceita somente letras
            if (bi.length() == 12) {
                if (!Character.isLetter(c)) {
                    event.consume(); // Bloqueia se não for uma letra
                    txtBI.setStyle("-fx-text-fill: red;"); // Muda a cor para vermelho se for inválido
                } else {
                    txtBI.setStyle("-fx-text-fill: black;"); // Mantém a cor preta para caracteres válidos
                }
            } // Limita o número de caracteres a 13 (12 números + 1 letra)
            else if (bi.length() >= 13) {
                event.consume(); // Impede digitar mais de 13 caracteres
                txtBI.setStyle("-fx-text-fill: red;"); // Muda a cor para vermelho quando ultrapassa o limite
            } // Se menos de 12 caracteres, só aceita números
            else {
                if (!Character.isDigit(c)) {
                    event.consume(); // Bloqueia se não for um número
                    txtBI.setStyle("-fx-text-fill: red;"); // Muda a cor para vermelho se for inválido
                } else {
                    txtBI.setStyle("-fx-text-fill: black;"); // Mantém a cor preta para números válidos
                }
            }
        });
    }

    public void validarTelefoneComRegex() {
        txtTelefone.setOnKeyTyped(event -> {
            String telefone = txtTelefone.getText();
            char c = event.getCharacter().charAt(0);

            // Permitir apenas números
            if (!Character.isDigit(c)) {
                event.consume(); // Bloqueia a entrada de caracteres não numéricos
            }

            // Limitar o comprimento a 10 dígitos
            if (telefone.length() >= 10) {
                event.consume(); // Bloqueia mais caracteres após 10
            }
        });
    }

    public void validarEmailComRegex() {
        txtEmail.setOnKeyReleased(event -> {
            String email = txtEmail.getText();
            // Regex para validar email
            String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

            if (!email.matches(regex)) {
                // Se o texto não corresponde ao padrão, exibe a mensagem de erro
                txtEmail.setStyle("-fx-text-fill: red;"); // Coloca o texto em vermelho para indicar erro
            } else {
                txtEmail.setStyle("-fx-text-fill: black;"); // Volta ao normal se estiver correto
            }
        });
    }

 
public void verificarCamposVazios() {
    if (txtNome.getText().trim().isEmpty()) {
        mostrarAlerta("O campo Nome está vazio.");
    } else if (txtUsername.getText().trim().isEmpty()) {
        mostrarAlerta("O campo Username está vazio.");
    } else if (txtEmail.getText().trim().isEmpty()) {
        mostrarAlerta("O campo Email está vazio.");
    } else if (txtEndereco.getText().trim().isEmpty()) {
        mostrarAlerta("O campo Endereço está vazio.");
    } else if (txtPassword.getText().trim().isEmpty()) {
        mostrarAlerta("O campo Password está vazio.");
    } else if (txtTelefone.getText().trim().isEmpty()) {
        mostrarAlerta("O campo Telefone está vazio.");
    } else if (dataDeNascimento.getValue() == null) { // Para o DatePicker no JavaFX
        mostrarAlerta("O campo Data de Nascimento está vazio.");
    } else {
        // Todos os campos estão preenchidos, prosseguir.
    }
}

private void mostrarAlerta(String mensagem) {
    Alert alerta = new Alert(AlertType.WARNING);
    alerta.setTitle("Campo vazio");
    alerta.setHeaderText(null);
    alerta.setContentText(mensagem);
    alerta.showAndWait();
}

}
