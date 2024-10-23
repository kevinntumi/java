/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.repository.servico;

import edu.uem.sgh.dao.ServicoDao;
import edu.uem.sgh.model.Result;
import edu.uem.sgh.model.Servico;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public class RemoteServicoRepository extends AbstractServicoRepository implements ServicoDao{
    public RemoteServicoRepository(Connection connection, String tblName) {
        super(connection, tblName);
    }

    @Override
    public Result<Servico> get(long id) {
        return super.get(id); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public Result<List<Servico>> getAll() {
        return super.getAll(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public Result<Boolean> deleteOrUndelete(long id, boolean delete) {
        return super.deleteOrUndelete(id, delete); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public Result<Boolean> edit(edu.uem.sgh.schema.Servico servico) {
        return super.edit(servico); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public Result<Boolean> add(edu.uem.sgh.schema.Servico servico) {
        return super.add(servico); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public Connection getConnection() {
        return super.getConnection(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }   
}