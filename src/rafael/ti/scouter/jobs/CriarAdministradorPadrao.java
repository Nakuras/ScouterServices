package rafael.ti.scouter.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import rafael.ti.scouter.dao.UsuarioDAO;
import rafael.ti.scouter.model.Usuario;
import rafael.ti.scouter.model.TipoUsuario;

@Component
public class CriarAdministradorPadrao implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UsuarioDAO usuarioDao;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		Usuario proprietario = new Usuario();
		proprietario.setEmail("adriano@scouter.com.br");
		proprietario.setSenha("proprietarioadriano");
		proprietario.setNome("Adriano");
		proprietario.setNumero("975843526");
		proprietario.setTipo(TipoUsuario.PROPRIETARIO);
		proprietario.hashearSenha();
		 
		
		if(usuarioDao.buscarPorEmailESenha(proprietario.getEmail(), proprietario.getSenha()) == null) {
			System.out.println(proprietario.getSenha());
			usuarioDao.inserir(proprietario);
		}
	}

}
