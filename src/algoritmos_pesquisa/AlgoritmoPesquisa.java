package algoritmos_pesquisa;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Kevin Ntumi
 */
public class AlgoritmoPesquisa {
    public static int sequencial(int[] arr, int elemento) {
        for (int i = 0 ; i < arr.length ; i++) {
            if (arr[i] == elemento) {
                return i;
            }
        }
        
        return -1;
    }
    
    public static int binaria(int[] arr, int chave) {
        return -1;
    }
    
    private static int dos(int[] arr, int j, int k, int chave) {
        if ((k < j)|| (chave < 0) || (chave > k)) return -1;
        
        int metade = (j + k) / 2;
 
        if (chave < metade) {
            return dos(arr, j, metade, chave);
        } else if (chave > metade) {
            return dos(arr, metade, arr.length - 1, chave);
        } else {
            return chave;
        }
    }
    
    public static void main(String[] args) {
        int[] arr = new int[] {
            20,25,30,35,40,45
        };
        
        System.out.println(dos(arr, 0, arr.length - 1, 5));
    }
}