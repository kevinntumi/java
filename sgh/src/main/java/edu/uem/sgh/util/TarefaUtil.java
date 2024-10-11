/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.util;

import javafx.concurrent.Task;

/**
 *
 * @author Kevin Ntumi
 */
public class TarefaUtil {
    public static void interromperTarefa(Task<?> tarefa) {
        if (tarefa.isRunning()) 
            tarefa.cancel(true);
    }
 
    public static void interromperTarefaRecursivamente(int numTentativa, int numTentativasMaximas, Task<?> tarefa) {
        if (numTentativa < 0 || numTentativa > numTentativasMaximas || !tarefa.isRunning()) return;
        interromperTarefa(tarefa);
        interromperTarefaRecursivamente(numTentativa + 1, numTentativasMaximas, tarefa);
    }
}
