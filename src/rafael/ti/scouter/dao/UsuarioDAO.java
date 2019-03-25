package rafael.ti.scouter.dao;

import org.springframework.transaction.annotation.Transactional;

import rafael.ti.scouter.model.Usuario;

public interface UsuarioDAO extends DAO<Usuario> {

	@Transactional
	public Usuario buscarPorEmailESenha(String email, String senha);
}
