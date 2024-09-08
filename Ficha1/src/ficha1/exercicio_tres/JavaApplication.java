/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ficha1.exercicio_tres;

import ficha1.exercicio_dois.Funcionario;
import ficha1.exercicio_dois.Gerente;
import ficha1.exercicio_dois.Vendedor;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Kevin Ntumi
 */
public class JavaApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        lerDados(funcionarios, sc);
        imprimirArrayList(funcionarios);
    }
    
    static void lerDados(ArrayList<Funcionario> funcionarios, Scanner sc) {
        boolean continuarInsercao = true;
                
        while (continuarInsercao) {
            int n;
            
            do {
                System.out.println("-------------------------------------");
                System.out.println("Tipos de funcionarios disponiveis:");
                System.out.println("1. Vendedor");
                System.out.println("2. Gerente");
                System.out.println("-------------------------------------");
                System.out.println("Escolha o tipo de funcionario que deseja inserir pelo numero:");
                n = sc.nextInt();
            } while (n < 1 || n > 2);
            
            System.out.println();
            
            if (n == 1) {
                funcionarios.add(obterVendedor(sc));
            } else {
                funcionarios.add(obterGerente(sc));
            }
            
            char resposta;
            
            do {
                System.out.println("Deseja continuar a inserir funcionarios? S/N?");
                
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
    
    static Vendedor obterVendedor(Scanner sc) {
        Vendedor vendedor = new Vendedor();
        vendedor.setNome(obterNome(sc));
        vendedor.setIdade(obterIdade(sc));
        vendedor.setSalario(obterSalario(sc));
        vendedor.setSexo(obterSexo(sc));
        vendedor.setComissao(obterComissao(sc));
        vendedor.setEstaDemitido(obterEstadoDemissao(sc));
        return vendedor;
    }
    
    static Gerente obterGerente(Scanner sc) {
        Gerente gerente = new Gerente();
        gerente.setNome(obterNome(sc));
        gerente.setIdade(obterIdade(sc));
        gerente.setSalario(obterSalario(sc));
        gerente.setSexo(obterSexo(sc));
        gerente.setBonus(obterBonus(sc));
        gerente.setEstaDemitido(obterEstadoDemissao(sc));
        return gerente;
    }
    
    static int obterComissao(Scanner sc) {
        int comissao;
        
        do {
            System.out.println("Digite a comissao");
            comissao = sc.nextInt();
        } while (comissao < 1 || comissao > 100);
        
        return comissao;
    }
    
    static int obterBonus(Scanner sc) {
        int bonus;
        
        do {
            System.out.println("Digite o bonus");
            bonus = sc.nextInt();
        } while (bonus < 1);
        
        return bonus;
    }
    
    static int obterIdade(Scanner sc) {
        int idade;
        
        do {
            System.out.println("Digite a idade");
            idade = sc.nextInt();
        } while (idade < 0);
        
        return idade;
    }
    
    static char obterSexo(Scanner sc) {
        String sexo;
            
        do {
            System.out.println("Digite o sexo");

            try {
                sexo = sc.next();
            } catch (Exception e) {
                sexo = "";
            }    
            
        } while (sexo.isBlank() || (!sexo.startsWith("F") && !sexo.startsWith("M")));
        
        return sexo.charAt(0);
    }
    
    static boolean obterEstadoDemissao(Scanner sc) {
        Boolean estadoDemissao = null;
        
        do {
            System.out.println("Demitir este funcionario? S/N");
            char r = sc.next().charAt(0);
            
            if (r == 'S' || r == 'N') estadoDemissao = (r == 'S');
            
        } while (estadoDemissao == null);
        
        return estadoDemissao;
    }
    
    static double obterSalario(Scanner sc) {
        double salario;
        
        do {
            System.out.println("Digite o salario");
            salario = sc.nextDouble();
        } while (salario < 0 || salario > Double.MAX_VALUE);
        
        return salario;
    }
    
    static String obterNome(Scanner sc) {
        String nome;
        
        do {
            System.out.println("Digite o nome");
            nome = sc.nextLine();
        } while (nome.isBlank());
  
        return nome;
    }

    static void imprimirArrayList(ArrayList<Funcionario> funcionarios) {
        for (Funcionario f : funcionarios) FolhaDePagamento.processarPagamento(f);
    }
}
