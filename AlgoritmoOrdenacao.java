/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Kevin Ntumi
 */
public class AlgoritmoOrdenacao {
    private static void bubbleSort(int[] arr, boolean ordenarDeFormaCrescente, int n) {
        int tamanho = (arr.length - 1);
        if ((n < 0) || (n + 1) > tamanho) return;
        
        if (ordenarDeFormaCrescente) {
            ordenarFormaCrescenteBubbleSort(arr, 0, tamanho);
        } else {
            ordenarFormaDecrescenteBubbleSort(arr, 0, tamanho);
        }
        
        bubbleSort(arr, ordenarDeFormaCrescente, n + 1);
    }
    
    private static void ordenarFormaCrescenteBubbleSort(int[] arr, int j, int k) {
        int i;

        for (i = j ; i < k ; i++) { 
            if (arr[i] > arr[i + 1]) {
                trocarValores(arr[i], arr[i + 1], i, i + 1, arr);
            }else {
                break;
            }
        }
    }
    
    private static void ordenarFormaDecrescenteBubbleSort(int[] arr, int j, int k) {
        int i;

        for (i = j ; i < k ; i++) { 
            if (arr[i + 1] > arr[i]) {
                trocarValores(arr[i], arr[i + 1], i, i + 1, arr);
            }
        }
    }
    
    //-----------------------------------------------------------------------------------
    
    static void insertionSort(int[] arr, boolean ordenarDeFormaCrescente, int n) {
        if (n == arr.length || n < 1) return;
        if (n == 1 && ordenarDeFormaCrescente) ordenarDeFormaCrescenteInsertionSort(arr, n, n);
        if (n == 1 && !ordenarDeFormaCrescente) ordenarDeFormaDecrescenteInsertionSort(arr, n, n);
        if (n != 1 && ordenarDeFormaCrescente) ordenarDeFormaCrescenteInsertionSort(arr, n, n + 1);
        if (n != 1 && !ordenarDeFormaCrescente) ordenarDeFormaDecrescenteInsertionSort(arr, n, n + 1);        
        insertionSort(arr, ordenarDeFormaCrescente, n + 1);
    }
    
    static void ordenarDeFormaCrescenteInsertionSort(int[] arr, int j, int k) {
        if (j == 1 && j == k) {
            if (arr[j] < arr[j - 1]) trocarValores(arr[j], arr[j - 1], j, j - 1, arr);
        } else {
            for (int i = j ; i < k ; i++) { 
                if (arr[i] < arr[i - 1]) {
                    trocarValores(arr[i], arr[i - 1], i, i - 1, arr);
                    ordenarDeFormaCrescenteInsertionSort(arr, 1, i);
                    break;
                }
            }
        }
    }
    
    static void ordenarDeFormaDecrescenteInsertionSort(int[] arr, int j, int k) {
        if (j == 1 && j == k) {
            if (arr[j] > arr[j - 1]) trocarValores(arr[j], arr[j - 1], j, j - 1, arr);
        } else {
            for (int i = j ; i < k ; i++) { 
                if (arr[i] > arr[i - 1]) {
                    trocarValores(arr[i], arr[i - 1], i, i - 1, arr);
                    ordenarDeFormaDecrescenteInsertionSort(arr, 1, i);
                }
            }
        }
    }
    
    //-----------------------------------------------------------------------------------
    
    
    static void trocarValores(int valorATrocar, int valor, int posicaoValorATrocar, int posicao, int[] arr) {
        arr[posicao] = valorATrocar;
        arr[posicaoValorATrocar] = valor;
    }
    
    //-----------------------------------------------------------------------------------
    
    static void seletionSort(int[] arr, boolean ordenarDeFormaCrescente, int n) {
        if (n == arr.length - 1) return;
        
        int posicao;
        
        if (ordenarDeFormaCrescente) {
            posicao = encontrarPosicaoMenorValor(arr, n);
        } else {
            posicao = encontrarPosicaoMaiorValor(arr, n);
        }
        
        trocarValores(arr[posicao], arr[n], posicao, n, arr);
        
        seletionSort(arr, ordenarDeFormaCrescente, n + 1);
    }
    
    static int encontrarPosicaoMenorValor(int [] arr, int n) {
        int menor = arr[n], posicaoMenorValor = n;
                
        for (int i = n ; i < arr.length ; i++) { 
            if (arr[i] < menor) {
                menor = arr[i];
                posicaoMenorValor = i;
            } 
        }
        
        return posicaoMenorValor;
    }
    
    static int encontrarPosicaoMaiorValor(int[] arr, int n) {
        int maior = arr[n], posicaoMaiorValor = n;
        
        for (int i = n ; i < arr.length ; i++) { 
            if (arr[i] > maior) {
                maior = arr[i];
                posicaoMaiorValor = i;
            }
        }
        
        return posicaoMaiorValor;
    }
}
