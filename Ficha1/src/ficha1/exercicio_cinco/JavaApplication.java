/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ficha1.exercicio_cinco;

/**
 *
 * @author Kevin Ntumi
 */
public class JavaApplication {
    /**
    * Função recursiva que recebe a string original e a nova bem como um número i e no fim retorna se a string original é palíndromo.
    * @param original a string na qual se procura o seu palíndromo
    * @param nova a string original invertida na qual irá ser comparada a original para verificar se de facto a original é palíndromo
    * @param i a posição usada para encontrar o caracter na string original. O i deve sempre ser o tamanho da string original menos 1
    * @return verdadeiro quando a string original é palíndromo.
    * @throws RuntimeException quando qualquer das strings for null, estiver vazia ou conter apenas espaços e quando o i for menor que 0
    **/
    private static boolean verificarPalindrome(String original, String nova, int i) {
        if (original == null) throw new RuntimeException("A string original não pode ser um null!");
        if (i >= original.length()) return verificarPalindrome(original, nova.concat("" + original.charAt(i)), original.length() - 1);
        if (nova == null) throw new RuntimeException("A string nova não pode ser um null!");
        if (i < 0) throw new RuntimeException("A posição não pode ser um número negativo!");
        if (i == 0) return nova.concat("" + original.charAt(i)).equals(original);
        return verificarPalindrome(original, nova.concat("" + original.charAt(i)), i - 1);
    }
    
    private static int somaDigitos(int n) {
        if (n == 0) return n;
        return (n % 10) + somaDigitos(n / 10);
    }
    
    private static int obterNumRepeticoes(int n, int k) {
        if (n == 0) return 0;
        if ((n % 10) == k) return 1 + obterNumRepeticoes(n / 10, k);
        return obterNumRepeticoes(n / 10, k);
    }
}