/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.helper;

/**
 *
 * @author Kevin Ntumi
 */
public enum ServicoSituacao {
    EM_MANUNTENCAO,
    EM_FUNCIONAMENTO;
    
    public static ServicoSituacao obterViaString(String servicoSituacaoStr) {
        if (servicoSituacaoStr == null || servicoSituacaoStr.isBlank())
            return null;
        
        ServicoSituacao[] servicoSituacao = ServicoSituacao.values();
        
        for (ServicoSituacao situacao : servicoSituacao) {
            String str = situacao.toString();
            
            if (servicoSituacaoStr.equals(str))
                return situacao;
        }
        
        return null;
    }
    
    public static String obterPorValor(ServicoSituacao servicoSituacao) {
        if (servicoSituacao == null)
            return null;
        
        return servicoSituacao.toString();
    }
}
