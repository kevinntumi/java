/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.dao;

import edu.uem.sgh.model.Quarto;
import edu.uem.sgh.model.Result;
import java.io.FileInputStream;
import java.util.List;

/**
 *
 * @author Kevin Ntumi
 */
public interface QuartoDao {
    public Result<Quarto> get(long id);
    public Result<List<Quarto>> getAll();
    public Result<Boolean> deleteOrUndelete(long id, boolean delete);
    public Result<Boolean> edit(Quarto t, FileInputStream fis);
    public Result<Boolean> add(Quarto quarto, FileInputStream fis);
}
