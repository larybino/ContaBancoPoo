package teste;

public class TesteInterface {
    public static void main(String[] args) {
        Animal gato = new Gato();
        gato.emitirSom();

        Animal vaca = new Vaca();
        vaca.emitirSom();
    }
}
