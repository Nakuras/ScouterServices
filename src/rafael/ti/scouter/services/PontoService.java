package rafael.ti.scouter.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import rafael.ti.scouter.dao.PontoDAO;
import rafael.ti.scouter.exceptions.EntidadeNaoEncontradaException;
import rafael.ti.scouter.exceptions.ValidacaoException;
import rafael.ti.scouter.model.Ponto;

@Service
public class PontoService {

	@Autowired
	private PontoDAO pontoDAO;

	public List<Ponto> buscarPorUsuario(Ponto ponto, BindingResult bindingResult) throws ValidacaoException, EntidadeNaoEncontradaException {
		if(bindingResult.hasFieldErrors("usuario")) {
			throw new ValidacaoException();
		}
		
		List<Ponto> pontoBuscado = pontoDAO.buscarPorUsuario(ponto.getUsuario());
		if(pontoBuscado == null) {
			throw new EntidadeNaoEncontradaException();
		}
		
		return pontoBuscado;
	}

	public Ponto cadastrar(Ponto ponto, BindingResult bindingResult) throws ValidacaoException {

		// Verifica possíveis erros no usuário
		if (bindingResult.hasErrors()) {
			throw new ValidacaoException();
		} else {

			return ponto;
		}

	}
	
	public Ponto buscar(Long id) throws EntidadeNaoEncontradaException{
		return pontoDAO.buscar(id);
	}
	
	public List<Ponto> buscarTodos() throws EntidadeNaoEncontradaException {
		return pontoDAO.buscarTodos(); 
	}
	
}