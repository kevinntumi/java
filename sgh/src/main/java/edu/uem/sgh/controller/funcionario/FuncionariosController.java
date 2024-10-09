/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.controller.funcionario;

import edu.uem.sgh.dao.FuncionarioDAO;
import edu.uem.sgh.model.Funcionario;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author PAIN
 */
public class FuncionariosController {
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
        JOptionPane.showMessageDialog(null, "Hóspede cadastrado com sucesso!");

    }
    
}
