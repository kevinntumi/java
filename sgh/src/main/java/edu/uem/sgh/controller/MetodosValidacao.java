package edu.uem.sgh.controller;

import java.awt.Color;
import java.awt.TextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author PAIN
 */
public class MetodosValidacao {

    public static void impedirNumeros(JTextField obj) {
        obj.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // Verifica se o caractere digitado é um número
                if (Character.isDigit(c)) {
                    e.consume(); // Impede o caractere de ser adicionado ao campo de texto
                }
            }
        });
    }

    public void validarBIComRegex(Object obj) {

        String bi = (String) obj;
        // Expressão regular: 12 números seguidos por uma letra
        String regex = "^\\d{12}[a-zA-Z]?$";

        if (!bi.matches(regex)) {
            JOptionPane.showMessageDialog(null, "verfique o campo BI", "Erro", JOptionPane.ERROR_MESSAGE);
            // Se o texto não corresponde ao padrão, exibe uma mensagem ou sinaliza erro
        } else {

        }
    }

    public void validarBIComLimite(TextField obj) {
        obj.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String bi = obj.getText();
                char c = e.getKeyChar();

                // Se já tem 12 caracteres, aceita somente letras
                if (bi.length() == 12) {
                    if (!Character.isLetter(c)) {
                        e.consume(); // Bloqueia se não for uma letra
                        obj.setForeground(Color.RED); // Muda cor para vermelho se for inválido
                    } else {
                        obj.setForeground(Color.BLACK); // Mantém a cor preta para caracteres válidos
                    }
                } // Limita o número de caracteres a 13 (12 números + 1 letra)
                else if (bi.length() >= 13) {
                    e.consume(); // Impede digitar mais de 13 caracteres
                    obj.setForeground(Color.RED); // Muda a cor para vermelho quando ultrapassa o limite
                } // Se menos de 12 caracteres, só aceita números
                else {
                    if (!Character.isDigit(c)) {
                        e.consume(); // Bloqueia se não for um número
                        obj.setForeground(Color.RED); // Muda cor para vermelho se for inválido
                    } else {
                        obj.setForeground(Color.BLACK); // Mantém a cor preta para números válidos
                    }
                }
            }
        });
    }

    public void validarEnderecoComRegex(TextField obj) {

        String endereco = obj.getText();
        // Expressão regular: apenas letras e números
        String regex = "^[a-zA-Z0-9]*$";

        if (!endereco.matches(regex)) {
            // Se o texto não corresponde ao padrão, exibe uma mensagem ou sinaliza erro
            JOptionPane.showMessageDialog(null, "verfique o campo Endereço", "Erro", JOptionPane.ERROR_MESSAGE);

        } else {
            System.out.println("");
        }

    }

    public void validarTelefoneComRegex(TextField obj) {
        obj.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // Permitir apenas números
                if (!Character.isDigit(c)) {
                    e.consume(); // Bloqueia a entrada de caracteres não numéricos
                }
                // Limitar o comprimento a 10 dígitos, por exemplo
                if (obj.getText().length() >= 10) {
                    e.consume(); // Bloqueia mais caracteres após 10
                }
            }
        });
    }

    public void validarSenhaComRegex(Object obj) {

        String password = (String) obj;
        // Regex para 4 letras e 2 números
        String regex = "^(?=(?:[^0-9]*[0-9]){2})(?=(?:[^a-zA-Z]*[a-zA-Z]){4})[a-zA-Z0-9]{6}$";

        if (!password.matches(regex)) {
            // Se o texto não corresponde ao padrão, exibe a mensagem de erro
            JOptionPane.showMessageDialog(null, "verfique o campo Senha", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {

        }
    }

    public void validarEmailComRegex(Object obj) {

        String email = (String) obj;
        // Regex para validar email
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        if (!email.matches(regex)) {
            // Se o texto não corresponde ao padrão, exibe a mensagem de erro
            JOptionPane.showMessageDialog(null, "verfique o campo Senha", "Erro", JOptionPane.ERROR_MESSAGE);

        } else {

        }
    }

    /*public void verificarCamposVazios() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Nome está vazio.");
        } else if (txtUsername.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Username está vazio.");
        } else if (txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Email está vazio.");
        } else if (txtEndereco.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Endereço está vazio.");
        } else if (txtPassword.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Password está vazio.");
        } else if (txtTelefone.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Telefone está vazio.");
        } else if (dataNascimento.getDate() == null) {
            JOptionPane.showMessageDialog(null, "O campo Data de Nascimento está vazio.");
        } else {
            cadastrarFuncionario(); // Chama o método cadastrar se todos os campos estiverem preenchidos
        }
    }*/
}
