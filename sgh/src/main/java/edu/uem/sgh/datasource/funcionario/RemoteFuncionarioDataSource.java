package edu.uem.sgh.datasource.funcionario;

import com.mysql.cj.xdevapi.PreparableStatement;
import edu.uem.sgh.datasource.AbstractDataSource;
import edu.uem.sgh.model.Funcionario;
import edu.uem.sgh.model.Result;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author PAIN
 */
public class RemoteFuncionarioDataSource extends AbstractDataSource {

    private String tableName;

    public RemoteFuncionarioDataSource(Connection connection, String tableName) {
        super(connection);
        this.tableName = tableName;
    }

    @Override
    public Connection getConnection() {
        return super.getConnection(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    public Result<Boolean> cadastrarFuncionario(String nome, String telefone, String morada, Date dataDenascimento, Date dataDeRegistro) {

        Result<Boolean> result = null;
        String sql = "INSERT INTO funcionarios (nome, data_nascimento, data_registo, email, documento, morada, telefone, status) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = getConnection().prepareStatement(sql);
            Funcionario funcionario = new Funcionario();
            // Definir os valores para cada campo da tabela
            preparedStatement.setString(1, funcionario.getNome()); // Nome
            preparedStatement.setLong(2, funcionario.getDataNascimento()); // Data de nascimento
            preparedStatement.setLong(3, funcionario.getDataRegisto()); // Data de registro
            // Definindo os novos campos
            preparedStatement.setString(4, funcionario.getEmail()); // Email
            preparedStatement.setString(5, funcionario.getNumBI()); // Documento

            preparedStatement.setString(8, funcionario.getMorada()); // Endereço
            preparedStatement.setString(9, funcionario.getTelefone()); // Telefone
            preparedStatement.setBoolean(10, true);
            // Executar o comando SQL
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println("");
        }
        return result;
    }

    public boolean demitirFuncionario(int idFuncionario) {
        String sql = "UPDATE funcionarios SET estado = ? WHERE id = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, false); // Atualiza o estado para 0
            preparedStatement.setInt(2, idFuncionario); // ID do funcionário a ser demitido

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

    public void atualizarDadosFuncionario(String telefone, String endereco, String email, String numDocumento, String usernameAux) {
        String sql = "UPDATE funcionarios SET  endereco = ?, email = ?, numBi = ? WHERE username = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setString(1, endereco);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, numDocumento);
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
