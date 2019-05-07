package rafael.ti.scouter.ws.rest.controllers;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTCreationException;

import rafael.ti.scouter.core.SessionUtils;
import rafael.ti.scouter.dao.ClienteDAO;
import rafael.ti.scouter.exceptions.EntidadeNaoEncontradaException;
import rafael.ti.scouter.exceptions.ValidacaoException;
import rafael.ti.scouter.model.Cliente;
import rafael.ti.scouter.services.ClienteService;
import rafael.ti.scouter.utils.MapUtils;

@RequestMapping(value = "/rest")
@RestController
public class ClienteRestController {

	@Autowired
	ClienteService clienteService;

	@Autowired
	ClienteDAO clienteDao;
	
	@Autowired
	SessionUtils sessionUtils;
	// ---------------------------------------------------------
	
	@RequestMapping(value = "/clientes/usuario", method = { RequestMethod.GET })
	public ResponseEntity<Object> gerarJwt(@Valid @RequestBody Cliente usuario, BindingResult bindingResult)
			throws IllegalArgumentException, JWTCreationException, UnsupportedEncodingException {

		try {
			clienteService.buscarPorUsuario(usuario, bindingResult);
			Map<String, String> mapaToken = new HashMap<>();
			return ResponseEntity.ok(mapaToken);		
			
		} catch (ValidacaoException e) {
			return ResponseEntity.unprocessableEntity().body(MapUtils.mapaDe(bindingResult));

		} catch (EntidadeNaoEncontradaException e) {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("X-Reason", "Credenciais inválidas").build();
		}
	}

	/* --------------------------------------------------------- */
	
	@GetMapping("/clientes")
	public ResponseEntity<Object> buscarTodos() {

		try {

			return ResponseEntity.ok(clienteService.buscarTodos());

		} catch (EntidadeNaoEncontradaException e) {

			return ResponseEntity.notFound().build();

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(500).build();

		}

	}

	/* --------------------------------------------------------- */
	
	@GetMapping("/cliente/{id}")
	public ResponseEntity<Cliente> getcliente(@PathVariable("id") Long id) {
		try {
			return ResponseEntity.ok(clienteService.buscar(id));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}
	
	/* --------------------------------------------------------- */
	
	@PostMapping(value = "/cliente/novo")
	public ResponseEntity<Cliente> createcliente(@RequestBody Cliente cliente) {
		System.out.println("Creating cliente " + cliente.getNome());

		clienteDao.inserir(cliente);

		return new ResponseEntity<Cliente>(HttpStatus.CREATED);
	}

	/* --------------------------------------------------------- */

	@PutMapping(value = "/cliente/alterar/{id}")
	public ResponseEntity<Cliente> updatecliente(@PathVariable("id") long id, @RequestBody Cliente cliente) {
		System.out.println("Updating cliente " + id);

		Cliente clienteBuscado = clienteDao.buscar(id);

		if (cliente == null) {
			System.out.println("cliente with id " + id + " not found");
			return new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND);
		}

		clienteBuscado.setNome(cliente.getNome());
		clienteBuscado.setIdade(cliente.getIdade());
		clienteBuscado.setSexo(cliente.getSexo());
		clienteBuscado.setTelefone(cliente.getTelefone());
		clienteBuscado.setWhatsApp(cliente.getWhatsApp());
		clienteBuscado.setResponsavel(cliente.getResponsavel());
		clienteBuscado.setDia(cliente.getDia());
		clienteBuscado.setHorario(cliente.getHorario());
		clienteBuscado.setStatus(cliente.getStatus());
		clienteBuscado.setLinkDeInstagram(cliente.getLinkDeInstagram());
		
		clienteDao.alterar(clienteBuscado);
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	/* --------------------------------------------------------- */

	@DeleteMapping(value = "/cliente/deletar/{id}")
	public ResponseEntity<Cliente> deletecliente(@PathVariable("id") long id) {
		System.out.println("Fetching & Deleting cliente with id " + id);

		Cliente cliente = clienteDao.buscar(id);
		if (cliente == null) {
			System.out.println("Unable to delete. cliente with id " + id + " not found");
			return new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND);
		}

		clienteDao.deletar(id);
		return new ResponseEntity<Cliente>(HttpStatus.NO_CONTENT);
	}

}
