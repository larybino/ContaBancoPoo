package visao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import controle.MovimentacaoControle;
import dao.ClienteDAO;
import dao.ContaDAO;
import entidade.Movimentacao;
import entidade.Cliente;
import entidade.Conta;

public class MovimentacaoTela {

	public static void main(String[] args) throws ParseException {
		ClienteDAO clienteDAO = new ClienteDAO();
		ContaDAO contaDAO = new ContaDAO();
		
		Cliente cliente = new Cliente();
		clienteDAO.inserir(cliente);

		Conta conta = new Conta();
		conta.setCliente(cliente);
		contaDAO.inserir(contaDAO);

		MovimentacaoControle controle = new MovimentacaoControle();
		
		Movimentacao movimentacao = new Movimentacao();
		cliente.setCpfCorrentista("12345678909");
		movimentacao.setDataTransacao(new Date());
		movimentacao.setDescricao("depósito de 500,00");
		cliente.setNomeCorrentista("Laryssa");
		movimentacao.setTipoTransacao("depósito");
		movimentacao.setValorOperacao(500.);	

		switch(movimentacao.getTipoTransacao()){
			case "saque":
				controle.realizarSaque(movimentacao);
				break;
			case "depósito":
				controle.realizarDeposito(movimentacao);
				break;
			case "pagamento":
				controle.realizarPagamento(movimentacao);
				break;
			case "pix":
				controle.realizarPix(movimentacao);
				break;
		}

		double saldo = controle.consultarSaldo(cliente.getCpfCorrentista());
		System.out.println("R$ " + saldo);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date inicio = sdf.parse("01/11/2024"); 
		Date fim = sdf.parse("31/11/2024");    
		List<Movimentacao> extrato = controle.consultarExtrato(conta.getId(), inicio, fim);
		if (extrato != null && !extrato.isEmpty()) {
			System.out.println("Extrato do período de " + sdf.format(inicio) + " a " + sdf.format(fim) + ":");
				for (Movimentacao mov : extrato) {
					System.out.println("Data: " + mov.getDataTransacao() + " | Descrição: " + mov.getDescricao() + " | Valor: R$ " + mov.getValorOperacao());
				}
		} else {
			System.out.println("Nenhuma movimentação encontrada para o período especificado.");
		}
	}	
}
