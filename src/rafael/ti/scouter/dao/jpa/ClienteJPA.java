package rafael.ti.scouter.dao.jpa;

import org.springframework.stereotype.Repository;

import rafael.ti.scouter.dao.ClienteDAO;
import rafael.ti.scouter.model.Cliente;

@Repository("clienteDao")
public class ClienteJPA extends AbstractJPA<Cliente> implements ClienteDAO{

	@Override
	public String getEntityName() {
		return "Cliente";
	}

}
