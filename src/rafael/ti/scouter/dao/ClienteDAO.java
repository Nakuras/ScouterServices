package rafael.ti.scouter.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import rafael.ti.scouter.model.Cliente;

public interface ClienteDAO extends DAO<Cliente>{
	
	@Transactional
	public List<Cliente> buscarPorUsuario(String usuario);

}
