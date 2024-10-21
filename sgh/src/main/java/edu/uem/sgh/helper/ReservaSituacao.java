/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.helper;

/**
 *
 * @author Kevin Ntumi
 */
public enum ReservaSituacao {
    CANCELADA,
    POR_CONFIRMAR,
    CONFIRMADA;
    
    public static ReservaSituacao obterViaString(String reservaSituacaoStr) {
        if (reservaSituacaoStr == null || reservaSituacaoStr.isBlank())
            return null;
        
        ReservaSituacao[] reservaSituacoes = ReservaSituacao.values();
        
        for (ReservaSituacao reservaSituacao : reservaSituacoes) {
            String str = reservaSituacao.toString();
            
            if (reservaSituacaoStr.equals(str))
                return reservaSituacao;
        }
        
        return null;
    }
    
    public static String obterPorValor(ReservaSituacao reservaSituacao) {
        if (reservaSituacao == null)
            return null;
        
        return reservaSituacao.toString();
    }
}
