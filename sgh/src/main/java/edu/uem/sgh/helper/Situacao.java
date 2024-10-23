/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.helper;

/**
 *
 * @author Kevin Ntumi
 */
public class Situacao {
    public static enum ServicoQuarto {
        ASSOCIADO,
        NAO_ASSOCIADO;
        
        public static String obterEmString(Situacao.ServicoQuarto situacaoServicoQuarto) {
            if (situacaoServicoQuarto == null)
                return null;
            else 
                return situacaoServicoQuarto.toString();
        }
        
        public static Situacao.ServicoQuarto obterViaString (String situacaoServicoQuarto) {
            if (situacaoServicoQuarto == null) {
                return null;
            }
            
            Situacao.ServicoQuarto[] servicoQuartoSituacoes = Situacao.ServicoQuarto.values();
        
            for (Situacao.ServicoQuarto servicoQuartoSituacao : servicoQuartoSituacoes) {
                String str = servicoQuartoSituacao.toString();

                if (situacaoServicoQuarto.equals(str))
                    return servicoQuartoSituacao;
            }

            return null;
        }
    }
}