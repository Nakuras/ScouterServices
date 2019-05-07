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
@Table(name = "ponto")
public class Ponto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "pontoId")
	private Long id;
	
	@Column(length = 32, nullable = false, unique = false)
	@Size(min = 1, max = 32, message = "{Size}")
	@NotNull(message = "{NotNull}")
	private String dia;
	
	@Column(length = 32, nullable = false, unique = false)
	@Size(min = 1, max = 32, message = "{Size}")
	@NotNull(message = "{NotNull}")
	private String horario;
	
	@Column(length = 32, nullable = false, unique = false)
	@Size(min = 1, max = 64, message = "{Size}")
	@NotNull(message = "{NotNull}")
	private String usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}
