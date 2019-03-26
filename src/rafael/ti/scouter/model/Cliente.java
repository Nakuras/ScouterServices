package rafael.ti.scouter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "IdCliente")
	private Long Id;
	
	@Column(length = 64, nullable = false, unique = false)
	@Size(min = 1, max = 128, message = "{Size}")
	@NotNull(message = "{NotNull}")
	private String nome;
	
	@Column(length = 32, nullable = false, unique = false)
	@Size(min = 1, max = 32, message = "{Size}")
	private String telefone;
	
	@Column(length = 32, nullable = false, unique = false)
	@Size(min = 1, max = 32, message = "{Size}")
	@NotNull(message = "{NotNull}")
	private String horario;
	
	@Column(length = 64, nullable = false, unique = false)
	@Size(min = 1, max = 64, message = "{Size}")
	@NotNull(message = "{NotNull}")
	private String status;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
