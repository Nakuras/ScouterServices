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
	
	@Column(length = 120, nullable = false, unique = false)
	@Size(min = 1, max = 120, message = "{Size}")
	@NotNull(message = "{NotNull}")
	private String usuario;
	
	@Column(length = 128, nullable = false, unique = false)
	@Size(min = 1, max = 128, message = "{Size}")
	@NotNull(message = "{NotNull}")
	private String nome;
	
	@Column(length = 32, nullable = false, unique = false)
	@Size(min = 1, max = 32, message = "{Size}")
	@NotNull(message = "{NotNull}")
	private String idade;
	
	@NotNull(message = "{NotNull}")
	private Sexo sexo;
	
	@Column(length = 256, nullable = false, unique = false)
	@Size(min = 4, max = 256, message = "{Size}")
	@NotNull(message = "{NotNull}")
	private String linkDeInstagram;
	
	@Column(length = 32, nullable = false, unique = false)
	@Size(min = 1, max = 32, message = "{Size}")
	@NotNull(message = "{NotNull}")
	private String telefone;
	
	@Column(length = 32, nullable = false, unique = false)
	private String whatsApp;
	
	@Column(length = 128, nullable = false, unique = false)
	private String responsavel;
	
	@Column(length = 128, nullable = false, unique = false)
	@Size(min = 1, max = 128, message = "{Size}")
	@NotNull(message = "{NotNull}")
	private String dia;
	
	@Column(length = 32, nullable = false, unique = false)
	@Size(min = 1, max = 32, message = "{Size}")
	@NotNull(message = "{NotNull}")
	private String horario;
	
	@NotNull(message = "{NotNull}")
	private Status status;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

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

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getIdade() {
		return idade;
	}

	public void setIdade(String idade) {
		this.idade = idade;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public String getLinkDeInstagram() {
		return linkDeInstagram;
	}

	public void setLinkDeInstagram(String linkDeInstagram) {
		this.linkDeInstagram = linkDeInstagram;
	}

	public String getWhatsApp() {
		return whatsApp;
	}

	public void setWhatsApp(String whatsApp) {
		this.whatsApp = whatsApp;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	
}
