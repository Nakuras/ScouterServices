package rafael.ti.scouter.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import rafael.ti.scouter.model.Ponto;

public interface PontoDAO extends DAO<Ponto>{
	
	@Transactional
	public List<Ponto> buscarPorUsuario(String usuario);

}
