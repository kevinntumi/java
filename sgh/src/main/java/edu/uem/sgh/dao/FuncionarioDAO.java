package edu.uem.sgh.dao;

import edu.uem.sgh.model.Funcionario;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author PAIN
 */
public class FuncionarioDAO {

    Connection conn;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    ArrayList<Funcionario> lista = new ArrayList<>();

    public void cadastrarFuncionario(Funcionario funcionario) {
        String sql = "INSERT INTO funcionarios (nome, data_nascimento, data_registo, email, documento, password, username, endereco, telefone, cargo, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        conn = new ConexaoDAO().conectaBD();
        try {
            preparedStatement = conn.prepareStatement(sql);

            // Definir os valores para cada campo da tabela
            preparedStatement.setString(1, funcionario.getNome()); // Nome

            // Converter data de nascimento para java.sql.Date
            java.util.Date dataNascimento = funcionario.getDataNascimento();
            java.sql.Date sqlDataNascimento = new java.sql.Date(dataNascimento.getTime());
            preparedStatement.setDate(2, sqlDataNascimento); // Data de nascimento

            // Converter data de registro para java.sql.Date
            java.util.Date dataRegisto = funcionario.getDataRegisto();
            java.sql.Date sqlDataRegisto = new java.sql.Date(dataRegisto.getTime());
            preparedStatement.setDate(3, sqlDataRegisto); // Data de registro

            // Definindo os novos campos
            preparedStatement.setString(4, funcionario.getEmail()); // Email
            preparedStatement.setString(5, funcionario.getDocumentoIdentificacao()); // Documento
            preparedStatement.setString(6, funcionario.getPassword()); // Password
            preparedStatement.setString(7, funcionario.getUsername()); // Username
            preparedStatement.setString(8, funcionario.getEndereco()); // Endereço
            preparedStatement.setString(9, funcionario.getTelefone()); // Telefone
            preparedStatement.setString(10, funcionario.getCargo()); // Cargo
            preparedStatement.setInt(11, funcionario.getStatus()); // Status

            // Executar o comando SQL
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "FuncionarioDAO: " + e.getMessage());
        }
    }

    public ArrayList<Funcionario> pesquisarFuncionario() {
        String sql = "select * from funcionarios";
        conn = new ConexaoDAO().conectaBD();
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(resultSet.getInt("id"));
                funcionario.setNome(resultSet.getString("nome"));
                funcionario.setDataNascimento(resultSet.getDate("data_nascimento"));

                funcionario.setDataRegisto(resultSet.getDate("data_registo"));

                lista.add(funcionario);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "FuncionarioDAO Pesquisar " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }
        return lista;
    }

    public static void main(String[] args) {
        System.out.print(1);
    }

    public boolean demitirFuncionario(int idFuncionario) {
        String sql = "UPDATE funcionarios SET estado = ? WHERE num_funcionario = ?";
        conn = new ConexaoDAO().conectaBD();
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, 0); // Atualiza o estado para 0
            preparedStatement.setInt(2, idFuncionario); // ID do funcionário a ser demitido

            int linhasAfetadas = preparedStatement.executeUpdate();
            return linhasAfetadas > 0; // Retorna true se a atualização foi bem-sucedida
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao demitir funcionário: " + e.getMessage());
            return false; // Retorna false se ocorrer um erro
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    public void atualizarDadosFuncionario(String telefone, String endereco, String email, String numDocumento, String usernameAux) {
        String sql = "UPDATE funcionarios SET  endereco = ?, email = ?, num_doc_id = ? WHERE username = ?";
        conn = new ConexaoDAO().conectaBD();

        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, endereco);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, numDocumento);
            preparedStatement.setString(4, usernameAux); // Usa o username como chave para identificar o usuário

            System.out.println(usernameAux);

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar dados: " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
    // Método para verificar login

    public boolean verificarLogin(String username, String password) {
        String sql = "SELECT * FROM funcionarios WHERE username = ? AND palavra_passe = ?";
        conn = new ConexaoDAO().conectaBD();
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Retorna true se encontrar uma linha correspondente
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar login: " + e.getMessage());
            return false;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
     public ArrayList<Funcionario> pesquisarFuncionarioGerente() {
        String sql = "select * from funcionarios WHERE cargo=gerente";
        conn = new ConexaoDAO().conectaBD();
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(resultSet.getInt("id"));
                funcionario.setNome(resultSet.getString("nome"));
                funcionario.setDataNascimento(resultSet.getDate("data_nascimento"));

                funcionario.setDataRegisto(resultSet.getDate("data_registo"));

                lista.add(funcionario);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "FuncionarioDAO Pesquisar " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }
        return lista;
    }
     
      public ArrayList<Funcionario> pesquisarFuncionarioRecepcionista() {
        String sql = "select * from funcionarios WHERE cargo=recepcionista";
        conn = new ConexaoDAO().conectaBD();
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(resultSet.getInt("id"));
                funcionario.setNome(resultSet.getString("nome"));
                funcionario.setDataNascimento(resultSet.getDate("data_nascimento"));

                funcionario.setDataRegisto(resultSet.getDate("data_registo"));

                lista.add(funcionario);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "FuncionarioDAO Pesquisar " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }
        return lista;
    }
}
