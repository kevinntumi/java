package edu.uem.sgh.datasource.hospede;

import edu.uem.sgh.datasource.AbstractDataSource;
import edu.uem.sgh.model.Hospede;
import edu.uem.sgh.model.Result;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author PAIN
 */
public class RemoteHospedeDataSource extends AbstractDataSource {

    private String tableName;

    public RemoteHospedeDataSource(Connection connection, String tableName) {
        super(connection);
        this.tableName = tableName;
    }

    @Override
    public Connection getConnection() {
        return super.getConnection(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    public Result<Boolean> cadastrarFuncionario(String nome, String telefone, String morada, Date dataDenascimento, Date dataDeRegistro) {

        Result<Boolean> result = null;
        String sql = "INSERT INTO hospedes (nome, data_nascimento, data_registo, email, documento, morada, telefone, status) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement;
        Hospede hospede = new Hospede();
        try {
            preparedStatement = getConnection().prepareStatement(sql);
            // Definir os valores para cada campo da tabela
            preparedStatement.setString(1, hospede.getNome()); // Nome
            preparedStatement.setLong(2, hospede.getDataNascimento()); // Data de nascimento
            preparedStatement.setLong(3, hospede.getDataRegistro()); // Data de registro
            // Definindo os novos campos
            preparedStatement.setString(4, hospede.getEmail()); // Email
            preparedStatement.setString(5, hospede.getNumBi()); // Documento

            preparedStatement.setString(6, hospede.getMorada()); // Endereço
            preparedStatement.setString(7, hospede.getTelefone()); // Telefone
            preparedStatement.setBoolean(8, true);
            // Executar o comando SQL
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println("");
        }
        return result;
    }

    public boolean apagarHospede(int idHospede) {
        String sql = "UPDATE hospedes SET estado = ? WHERE id = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, false); // Atualiza o estado para 0
            preparedStatement.setInt(2, idHospede); // ID do funcionário a ser demitido

            int linhasAfetadas = preparedStatement.executeUpdate();
            return linhasAfetadas > 0; // Retorna true se a atualização foi bem-sucedida
        } catch (SQLException e) {
            return false; // Retorna false se ocorrer um erro
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (getConnection() != null) {
                    getConnection().close();
                }
            } catch (SQLException e) {
            }
        }
    }

    public void atualizarDadosHospede(String telefone, String endereco, String email, String numBi, String usernameAux) {
        String sql = "UPDATE hospede SET  endereco = ?, email = ?, numBI = ? WHERE username = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setString(1, endereco);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, numBi);
            preparedStatement.setString(4, usernameAux); // Usa o username como chave para identificar o usuário

            System.out.println(usernameAux);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (getConnection() != null) {
                    getConnection().close();
                }
            } catch (SQLException e) {
            }
        }
    }

}
