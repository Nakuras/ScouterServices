package rafael.ti.scouter.dao.jpa;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import rafael.ti.scouter.dao.UsuarioDAO;
import rafael.ti.scouter.model.Usuario;

@Repository("usuarioDao")
public class UsuarioJPA extends AbstractJPA<Usuario> implements UsuarioDAO{

	@Override
	public Usuario buscarPorEmailESenha(String email, String senha) {
		List<Usuario> usuarios = buscarPorCampos(new HashMap<String, Object>() {
			{
				put("email", email);
				put("senha", senha);
			}
		});
		
		if(usuarios.isEmpty()) {
			return null;
		} else {
			return usuarios.get(0);
		}
	}

	@Override
	public String getEntityName() {
		return "Usuario";
	}

}
