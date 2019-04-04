package rafael.ti.scouter.dao.jpa;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import rafael.ti.scouter.dao.ClienteDAO;
import rafael.ti.scouter.model.Cliente;

@Repository("clienteDao")
public class ClienteJPA extends AbstractJPA<Cliente> implements ClienteDAO{

	@Override
	public String getEntityName() {
		return "Cliente";
	}

	@Override
	public List<Cliente> buscarPorUsuario(String usuario) {
		List<Cliente> clientes = buscarPorCampos(new HashMap<String, Object>() {
			{
				put("usuario", usuario);
			}

		});

		return clientes;
	}

}
