package edu.uem.sgh.controller;

/**
 *
 * @author PAIN
 */
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;

public class ValidationMethods {

    public void verificarCamposVazios(TextField txtNome, TextField txtEmail, TextField txtEndereco, TextField txtTelefone, DatePicker dataNascimento) {
        if (txtNome.getText().trim().isEmpty()) {
            mostrarAlerta("O campo Nome está vazio.");
        } else if (txtEmail.getText().trim().isEmpty()) {
            mostrarAlerta("O campo Email está vazio.");
        } else if (txtEndereco.getText().trim().isEmpty()) {
            mostrarAlerta("O campo Endereço está vazio.");
        } else if (txtTelefone.getText().trim().isEmpty()) {
            mostrarAlerta("O campo Telefone está vazio.");
        } else if (dataNascimento.getValue() == null) {
            mostrarAlerta("O campo Data de Nascimento está vazio.");
        } else {
            // cadastraarr
        }
    }

    public void validarTelefoneComRegex(TextField txtTelefone) {
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
    
  


    public void validarEmailComRegex(TextField txtEmail) {
        String email = txtEmail.getText();
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        if (!email.matches(regex)) {
            txtEmail.setStyle("-fx-text-fill: red;");
            mostrarAlerta("Email inválido. Ex: exemplo@dominio.com");
        } else {
            txtEmail.setStyle("-fx-text-fill: black;");
        }
    }

        public void impedirNumeros(TextField txtNome) {
        txtNome.setOnKeyTyped(event -> {
            String input = event.getCharacter();
            String text = txtNome.getText();

            // Verifica se o caractere digitado é um número
            if (input.matches("\\d")) {
                event.consume(); // Impede o caractere de ser adicionado ao campo de texto
            }

            // Se o campo está vazio e o usuário está digitando, força a primeira letra a ser maiúscula
            if (text.length() == 0 && input.matches("[a-zA-Z]")) {
                txtNome.setText(input.toUpperCase()); // Coloca a primeira letra como maiúscula
                event.consume(); // Consome o evento para evitar repetição
            }
        });
    }
    private void mostrarAlerta(String mensagem) {
        Alert alerta = new Alert(AlertType.WARNING);
        alerta.setTitle("Campo vazio");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
