package entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "conta")
public class Movimentacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "valor_operacao")
	private Double valorOperacao;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataTransacao;
	@Column(length = 150, name = "descricao", nullable = true, unique = false)
	private String descricao;
	private String tipoTransacao;
	private Conta conta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValorOperacao() {
		return valorOperacao;
	}

	public void setValorOperacao(Double valorOperacao) {
		this.valorOperacao = valorOperacao;
	}

	public Date getDataTransacao() {
		return dataTransacao;
	}

	public void setDataTransacao(Date dataTransacao) {
		this.dataTransacao = dataTransacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(String tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	

	@Override
	public String toString() {
		return "Movimentacao [id=" + id + ", valorOperacao=" + valorOperacao + ", dataTransacao=" + dataTransacao
				+ ", descricao=" + descricao + ", tipoTransacao=" + tipoTransacao + "]";
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

}
