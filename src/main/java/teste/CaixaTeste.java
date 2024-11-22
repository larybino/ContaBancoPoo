package teste;

public class CaixaTeste {
    public static void main(String[] args) {
        Caixa<String> caixaA = new Caixa<>(String.class);
        caixaA.colocar("Livro");
        System.out.println(caixaA.pegar());

        Caixa<Integer> caixaB = new Caixa<>(Integer.class);
        caixaB.colocar(123);
        System.out.println(caixaB.pegar());
    }
}
