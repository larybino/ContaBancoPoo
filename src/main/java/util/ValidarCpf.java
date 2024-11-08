package util;

public class ValidarCpf {
    public static boolean validar(String cpf) {
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}"))
            return false;
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * (10 - i);
        }
        int dig10 = (soma % 11 < 2) ? 0 : 11 - (soma % 11);
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * (11 - i);
        }
        int dig11 = (soma % 11 < 2) ? 0 : 11 - (soma % 11);
        return (dig10 == (cpf.charAt(9) - '0')) && (dig11 == (cpf.charAt(10) - '0'));
    }
}
