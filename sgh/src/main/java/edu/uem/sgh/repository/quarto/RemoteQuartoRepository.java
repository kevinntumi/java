/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.repository.quarto;

import edu.uem.sgh.model.Quarto;
import edu.uem.sgh.model.Result;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public class RemoteQuartoRepository extends AbstractQuartoRepository{
    public RemoteQuartoRepository(Connection connection) {
        super(connection);
    }
    
    @Override
    public Result<Quarto> get(long id) {
        return super.get(id);
    }

    @Override
    public Result<List<Quarto>> getAll() {
        return super.getAll();
    }
    
    @Override
    public Result<Boolean> deleteOrUndelete(long id, boolean delete) {
        return super.deleteOrUndelete(id, delete);
    }

    @Override
    public Result<Boolean> edit(Quarto t, File file) {
        return super.edit(t, file);
    }

    @Override
    public Result<Boolean> add(Quarto quarto, File file) {
        return super.add(quarto, file);
    }
}