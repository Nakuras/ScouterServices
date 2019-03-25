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
		
		Usuario dono = new Usuario();
		dono.setEmail("adriano@scouter.com.br");
		dono.setSenha("adrianodono");
		dono.setNome("Adriano");
		dono.setNumero("975843526");
		dono.setTipo(TipoUsuario.DONO);
		dono.hashearSenha();
		 
		
		if(usuarioDao.buscarPorEmailESenha(dono.getEmail(), dono.getSenha()) == null) {
			System.out.println(dono.getSenha());
			usuarioDao.inserir(dono);
		}
	}

}
