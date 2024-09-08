/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ficha1.exercicio_um;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Kevin Ntumi
 */
public class JavaApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<ContaCorrente> contas = new ArrayList<>();
        lerDados(contas, sc);
        imprimirDados(contas, sc);
    }
    
    static void lerDados(ArrayList<ContaCorrente> contas, Scanner sc) {
        boolean continuarInsercao = true;
                
        while (continuarInsercao) {
            int n;
            
            do {
                System.out.println("-------------------------------------");
                System.out.println("Tipos de contas disponiveis:");
                System.out.println("1. Corrente Normal");
                System.out.println("2. Corrente Especial");
                System.out.println("-------------------------------------");
                System.out.println("Escolha o tipo de conta que deseja inserir pelo numero:");
                n = sc.nextInt();
            } while (n < 1 || n > 2);
            
            System.out.println();
            
            if (n == 1) {
                contas.add(obterContaCorrenteNormal(sc));
            } else {
                contas.add(obterContaCorrenteEspecial(sc));
            }
            
            char resposta;
            
            do {
                System.out.println("Deseja continuar a abrir contas? S/N?");
                
                try {
                    resposta = sc.next().charAt(0);
                } catch (Exception e) {
                    resposta = ' ';
                }
                
            } while (resposta == ' ' || !(resposta == 'S' || resposta == 'N'));
            
            if (resposta == 'N') 
                continuarInsercao = false; 
                    System.out.println();
        }
    }
    
    static ContaCorrente obterContaCorrenteNormal(Scanner sc){
        ContaCorrente contaCorrente = new ContaCorrente();
        contaCorrente.modificarEstadoDaConta(obterEstadoConta(sc));
        
        if (contaCorrente.getEstado() == EstadoConta.ACTIVA) return contaCorrente;
        
        contaCorrente.depositar(obterQuantia(sc));
        return contaCorrente;
    }
    
    static ContaCorrenteEspecial obterContaCorrenteEspecial(Scanner sc){
        ContaCorrenteEspecial contaCorrenteEspecial = new ContaCorrenteEspecial();
        contaCorrenteEspecial.modificarEstadoDaConta(obterEstadoConta(sc));
        
        if (contaCorrenteEspecial.getEstado() == EstadoConta.ACTIVA) return contaCorrenteEspecial;
        
        contaCorrenteEspecial.depositar(obterQuantia(sc));
        return contaCorrenteEspecial;
    }
    
    static int obterQuantia(Scanner sc) {
        int quantia;
        
        do {
            System.out.println("Digite a quantia");
            quantia = sc.nextInt();
        } while (quantia < 0 || quantia > Double.MAX_VALUE);
        
        return quantia;
    }
    
    static boolean obterEstadoConta(Scanner sc) {
        Boolean estadoConta = null;
        
        do {
            System.out.println("Ativar esta conta? S/N");
            char r = sc.next().charAt(0);
 
            if (r == 'S' || r == 'N') estadoConta = (r == 'S');
            
        } while (estadoConta == null);
        
        return estadoConta;
    }
    
    private static void imprimirDados(ArrayList<ContaCorrente> contas, Scanner sc) {
        for (ContaCorrente contaCorrente : contas) {
            System.out.println(contaCorrente);
            
            if (contaCorrente.getEstado() == EstadoConta.DESACTIVADA) contaCorrente.modificarEstadoDaConta(obterEstadoConta(sc));
            if (contaCorrente.getEstado() == EstadoConta.DESACTIVADA) return;
            
            System.out.println("Inicio de deposito---------------------");
            contaCorrente.depositar(obterQuantia(sc));
            System.out.println("Fim de deposito------------------------");
            
            System.out.println();
            
            System.out.println("Inicio de levantamento------------------");
            contaCorrente.levantarQuantia(obterQuantia(sc));
            System.out.println("Fim de levantamento---------------------");
            
            System.out.println();
            
        }
    }
}