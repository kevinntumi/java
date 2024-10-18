/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.helper;

/**
 *
 * @author Kevin Ntumi
 */
public enum FuncionarioSituacao {
    DEMITIDO,
    CONTRATADO;
    
     public static FuncionarioSituacao obterViaString(String funcionarioSituacaoStr) {
        if (funcionarioSituacaoStr == null || funcionarioSituacaoStr.isBlank())
            return null;
        
        FuncionarioSituacao[] funcionarioSituacao = FuncionarioSituacao.values();
        
        for (FuncionarioSituacao situacao : funcionarioSituacao) {
            String str = situacao.toString();
            
            if (funcionarioSituacaoStr.equals(str))
                return situacao;
        }
        
        return null;
    }
    
    public static String obterPorValor(FuncionarioSituacao funcionarioSituacao) {
        if (funcionarioSituacao == null)
            return null;
        
        return funcionarioSituacao.toString();
    }
}