/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ficha1.exercicio_quatro;

import java.util.Scanner;

/**
 *
 * @author Kevin Ntumi
 */
public class Loja {
    public static void main(String[] args) {
        Produto[] produtos = new Produto[2];
        Scanner sc = new Scanner(System.in);
        lerProdutosDoTeclado(produtos, sc);
        imprimirProdutos(produtos);
    }
    
    static void lerProdutosDoTeclado(Produto[] produtos, Scanner sc) {
        int i = 0;
        
        while (i < produtos.length) {
            String produto = "";
            
            do {
                System.out.println("-------------------------------------");
                System.out.println("Lista de produtos disponiveis: ");
                System.out.println("Livro");
                System.out.println("CD");
                System.out.println("DVD");
                System.out.println("-------------------------------------");
                System.out.println("Insira o produto que deseja inserir:");
                produto = sc.nextLine();
            } while (!"CD".equals(produto) && !"DVD".equals(produto) && !"Livro".equals(produto));
            
            Produto p = null;
            String nome;
            double preco;
            
            do {
                System.out.println("Digite o nome do produto");
                nome = sc.nextLine();
            } while (nome.isBlank());
            
            do {
                System.out.println("Digite o preco");
                preco = sc.nextDouble();
            } while (preco < 0);
            
            switch (produto) {
                case "Livro": lerAutor(produtos, i, nome, preco, sc);
                    break;
                case "CD": lerNumFaixas(produtos, i, nome, preco, sc);
                    break;
                case "DVD": lerDuracao(produtos, i, nome, preco, sc);
                    break;
            }
            
            i++;
        }
    }
    
    static void lerAutor(Produto[] produtos, int i, String nome, double preco, Scanner sc) {
        Livro l = new Livro();
        String autor;
        
        do {
            System.out.println("Digite o autor");
            autor = sc.nextLine();
        } while (autor.isBlank());
        
        l.setAutor(autor);
        l.setNome(nome);
        l.setPreco(preco);
        produtos[i] = l;
    }
    
    static void lerNumFaixas(Produto[] produtos, int i, String nome, double preco, Scanner sc) {
        CD cd = new CD();
        int numFaixas;
        
        do {
            System.out.println("Digite o numero de faixas");
            numFaixas = sc.nextInt();
        } while (numFaixas < 0);
        
        cd.setNumFaixas(numFaixas);
        cd.setNome(nome);
        cd.setPreco(preco);
        produtos[i] = cd;
    }
    
    static void lerDuracao(Produto[] produtos, int i, String nome, double preco, Scanner sc) {
        DVD d = new DVD();
        int duracao;
        
        do {
            System.out.println("Digite a duracao");
            duracao = sc.nextInt();
        } while (duracao < 0);
        
        d.setDuracao(duracao);
        d.setNome(nome);
        d.setPreco(preco);
        produtos[i] = d;
    }

    static void imprimirProdutos(Produto[] produtos) {
        for (Produto produto : produtos) {
            System.out.println(produto);
        }
    }
}