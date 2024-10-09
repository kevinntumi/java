package edu.uem.sgh.dao;

import edu.uem.sgh.model.Hospede;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HospedeDAO {

    Connection conn;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    ArrayList<Hospede> lista = new ArrayList<>();
    public void cadastrarHospede(Hospede hospede) {
        String sql = "INSERT INTO hospede (nacionalidade, nome, data_nascimento, data_registo, email, documento, password, username, endereco, telefone, status, status_hospedagem, numQuarto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        conn = new ConexaoDAO().conectaBD();

        try {
            preparedStatement = conn.prepareStatement(sql);

            // Definir os valores para cada campo da tabela
            preparedStatement.setString(1, hospede.getNacionalidade()); // Nacionalidade
            preparedStatement.setString(2, hospede.getNome()); // Nome

            // Converter data de nascimento para java.sql.Date
            java.util.Date dataNascimento = hospede.getDataNascimento();
            java.sql.Date sqlDataNascimento = new java.sql.Date(dataNascimento.getTime());
            preparedStatement.setDate(3, sqlDataNascimento); // Data de nascimento

            // Converter data de registro para java.sql.Date
            java.util.Date dataRegisto = hospede.getDataRegisto();
            java.sql.Date sqlDataRegisto = new java.sql.Date(dataRegisto.getTime());
            preparedStatement.setDate(4, sqlDataRegisto); // Data de registro

            preparedStatement.setString(5, hospede.getEmail()); // Email
            preparedStatement.setString(6, hospede.getDocumentoIdentificacao()); // Documento
            preparedStatement.setString(7, hospede.getPassword()); // Password
            preparedStatement.setString(8, hospede.getUsername()); // Username
            preparedStatement.setString(9, hospede.getEndereco()); // Endereço
            preparedStatement.setString(10, hospede.getTelefone()); // Telefone
            preparedStatement.setInt(11, hospede.getStatus()); // Status
            preparedStatement.setString(12, hospede.getStatusHospedagem()); // Status de hospedagem
            preparedStatement.setInt(13, hospede.getNumQuarto()); // Número do quarto

            // Executar o comando SQL
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "HospedeDAO: " + e.getMessage());
        }
    }


    public ArrayList<Hospede> pesquisarTodosClientes() {
        ArrayList<Hospede> lista = new ArrayList<>();
        String sql = "SELECT * FROM hospedes"; // Supondo que a tabela seja 'hospedes'
        conn = new ConexaoDAO().conectaBD();

        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Hospede hospede = new Hospede();
                hospede.setId(resultSet.getInt("num_hospede"));
                hospede.setNome(resultSet.getString("nome"));
                hospede.setDocumentoIdentificacao(resultSet.getString("documento"));
                hospede.setTelefone(resultSet.getString("telefone"));
                hospede.setEmail(resultSet.getString("email"));
                hospede.setEndereco(resultSet.getString("endereco"));
                hospede.setDataNascimento(resultSet.getDate("data_nascimento"));
                hospede.setNumQuarto(resultSet.getInt("numero_quarto"));

                hospede.setStatus(resultSet.getInt("status")); // true = ativo, false = inativo

                lista.add(hospede); // Adiciona o hóspede à lista
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar clientes: " + e.getMessage());
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

    // Método para apagar logicamente um cliente (mudar o estado para false)
    public void apagarClienteLogicamente(int idHospede) {
        String sql = "UPDATE hospedes SET status = false WHERE num_hospede = ?";
        conn = new ConexaoDAO().conectaBD();

        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, idHospede);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente apagado logicamente com sucesso.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao apagar cliente: " + e.getMessage());
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
        String sql = "SELECT * FROM hospedes WHERE username = ? AND password = ?";
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

    // Método para pesquisar hóspedes ativos
    public ArrayList<Hospede> pesquisarHospedesAtivos() {
        String sql = "SELECT * FROM hospedes WHERE status = 1"; // Busca apenas os ativos
        conn = new ConexaoDAO().conectaBD();
        ArrayList<Hospede> lista = new ArrayList<>();

        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Hospede hospede = new Hospede();
                hospede.setId(resultSet.getInt("num_hospede"));
                hospede.setNome(resultSet.getString("nome"));
                hospede.setDocumentoIdentificacao(resultSet.getString("documento"));
                hospede.setTelefone(resultSet.getString("telefone"));
                hospede.setEmail(resultSet.getString("email"));
                hospede.setEndereco(resultSet.getString("endereco"));
                hospede.setDataNascimento(resultSet.getDate("data_nascimento"));
                hospede.setNumQuarto(resultSet.getInt("numero_quarto"));

                hospede.setStatusHospedagem(resultSet.getString("status").equals("1") ? "Ativo" : "Inativo"); // Armazena como String

                lista.add(hospede);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar hóspedes: " + e.getMessage());
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

    // Método para atualizar hóspede
    public void atualizarHospede(Hospede hospede) {
        String sql = "UPDATE hospedes SET telefone = ?, email = ?, endereco = ?, data_nascimento = ?, numero_quarto = ?, status_hospedagem = ?, username = ?, password = ? WHERE num_hospede = ?";
        conn = new ConexaoDAO().conectaBD();

        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, hospede.getTelefone());
            preparedStatement.setString(2, hospede.getEmail());
            preparedStatement.setString(3, hospede.getEndereco());
            preparedStatement.setDate(4, new java.sql.Date(hospede.getDataNascimento().getTime()));
            preparedStatement.setInt(5, hospede.getNumQuarto());
            preparedStatement.setString(8, hospede.getStatusHospedagem());
            preparedStatement.setString(9, hospede.getUsername()); // Novo campo
            preparedStatement.setString(10, hospede.getPassword()); // Novo campo
            preparedStatement.setInt(11, hospede.getId());

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Hóspede atualizado com sucesso.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar hóspede: " + e.getMessage());
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
     public ArrayList<Hospede> pesquisarHospedesPor() {
        String sql = "SELECT * FROM hospede WHERE status = 1"; // Busca apenas os ativos
        conn = new ConexaoDAO().conectaBD();
        ArrayList<Hospede> lista = new ArrayList<>();

        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Hospede hospede = new Hospede();
                hospede.setId(resultSet.getInt("num_hospede"));
                hospede.setNome(resultSet.getString("nome"));
                hospede.setDocumentoIdentificacao(resultSet.getString("documento"));
                hospede.setTelefone(resultSet.getString("telefone"));
                hospede.setEmail(resultSet.getString("email"));
                hospede.setEndereco(resultSet.getString("endereco"));
                hospede.setDataNascimento(resultSet.getDate("data_nascimento"));
                hospede.setNumQuarto(resultSet.getInt("numero_quarto"));

                hospede.setStatusHospedagem(resultSet.getString("status").equals("1") ? "Ativo" : "Inativo"); // Armazena como String

                lista.add(hospede);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar hóspedes: " + e.getMessage());
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
