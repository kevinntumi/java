/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.repository.servico_quarto;

import edu.uem.sgh.model.Result;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public class RemoteServicoQuartoRepository extends AbstractServicoQuartoRepository{
    public RemoteServicoQuartoRepository(Connection connection) {
        super(connection);
    }
    
    @Override
    public Result<edu.uem.sgh.model.ServicoQuarto> get(long id) {
        return super.get(id); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public Result<List<edu.uem.sgh.model.ServicoQuarto>> getAll() {
        return super.getAll(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public Result<Boolean> deleteOrUndelete(long id, boolean delete) {
        return super.deleteOrUndelete(id, delete); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public Result<Boolean> edit(edu.uem.sgh.request_body.ServicoQuarto servicoQuarto) {
        return super.edit(servicoQuarto); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public Result<Boolean> add(edu.uem.sgh.request_body.ServicoQuarto servicoQuarto) {
        return super.add(servicoQuarto); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }   
}