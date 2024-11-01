package servico;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import dao.ContaDAO;
import entidade.Conta;

public class ContaServico {
	ContaDAO dao = new ContaDAO();
	
	
	public Conta inserir(Conta conta) {
		conta.setDescricao("Operação de "+conta.getTipoTransacao());
		conta.setDataTransacao(new Date());
		Conta contaBanco = dao.inserir(conta);
		return contaBanco;
	}

	public void validarCpf(String cpf) throws Exception{
		if(!cpf.matches("\\d{11}")){
			throw new Exception("Cpf inválido");
		}
	}

	public void validarHorarioPix(String tipoTransacao) throws Exception {
        if (tipoTransacao.equals("Pix")) {
            LocalTime now = LocalTime.now();
            if (now.isBefore(LocalTime.of(6, 0)) || now.isAfter(LocalTime.of(22, 0))) {
                throw new Exception("Operações de Pix só podem ser realizadas entre 06:00 e 22:00.");
            }
        }
	}


}

	


	

	