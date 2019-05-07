package rafael.ti.scouter.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import rafael.ti.scouter.dao.ClienteDAO;
import rafael.ti.scouter.exceptions.EntidadeNaoEncontradaException;
import rafael.ti.scouter.exceptions.ValidacaoException;
import rafael.ti.scouter.model.Cliente;

@Service
public class ClienteService {

	@Autowired
	private ClienteDAO clienteDao;
	
	public List<Cliente> buscarPorUsuario(Cliente cliente, BindingResult bindingResult) throws ValidacaoException, EntidadeNaoEncontradaException {
		if(bindingResult.hasFieldErrors("usuario")) {
			throw new ValidacaoException();
		}
		
		List<Cliente> clienteBuscado = clienteDao.buscarPorUsuario(cliente.getUsuario());
		if(clienteBuscado == null) {
			throw new EntidadeNaoEncontradaException();
		}
		
		return clienteBuscado;
	}

	public Cliente cadastrar(Cliente cliente, BindingResult bindingResult) throws ValidacaoException {

		// Verifica poss�veis erros no usu�rio
		if (bindingResult.hasErrors()) {
			throw new ValidacaoException();
		}

		// Verifica se o e-mail do usu�rio j� esta em uso
		if (clienteDao.buscarPorCampo("nome", cliente.getNome()) != null) {
			bindingResult.addError(new FieldError("cliente", "nome", "O cliente j� est� no sistema"));
			throw new ValidacaoException();
		}

		clienteDao.inserir(cliente);

		return cliente;
	}

	public List<Cliente> buscarTodos() throws EntidadeNaoEncontradaException {

		return clienteDao.buscarTodos();
	}

	public Cliente buscar(Long id) throws EntidadeNaoEncontradaException {

		return clienteDao.buscar(id);
	}

}
