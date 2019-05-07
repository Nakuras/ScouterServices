package rafael.ti.scouter.ws.rest.controllers;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTCreationException;

import rafael.ti.scouter.dao.PontoDAO;
import rafael.ti.scouter.exceptions.EntidadeNaoEncontradaException;
import rafael.ti.scouter.exceptions.ValidacaoException;
import rafael.ti.scouter.model.Ponto;
import rafael.ti.scouter.services.PontoService;
import rafael.ti.scouter.utils.MapUtils;

@RequestMapping(value = "/rest")
@RestController
public class PontoRestController {
	
	@Autowired
	PontoService pontoService;
	
	@Autowired
	PontoDAO pontoDAO;
	
	// ------------------- Retrieve All entrada ---------------

		@GetMapping("/pontos")
		public ResponseEntity<Object> buscarTodos() {
		
			try {
				return ResponseEntity.ok(pontoService.buscarTodos());
				
			} catch (EntidadeNaoEncontradaException e) {
				return ResponseEntity.notFound().build();
			} catch (Exception e) {
			
				e.printStackTrace();
				
				return ResponseEntity.status(500).build();
			}
		}
		
		//---------------------------------------------------------
		
		@RequestMapping(value = "/pontos/usuario", method = { RequestMethod.GET })
		public ResponseEntity<Object> getpontoofusuario(@Valid @RequestBody Ponto ponto, BindingResult bindingResult)
				throws IllegalArgumentException, JWTCreationException, UnsupportedEncodingException {

			try {
				pontoService.buscarPorUsuario(ponto, bindingResult);
				Map<String, String> mapaToken = new HashMap<>();
				return ResponseEntity.ok(mapaToken);		
				
			} catch (ValidacaoException e) {
				return ResponseEntity.unprocessableEntity().body(MapUtils.mapaDe(bindingResult));

			} catch (EntidadeNaoEncontradaException e) {

				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("X-Reason", "Credenciais inválidas").build();
			}
		}
		// ------------------- Retrieve Single entrada ------------

		@GetMapping(value = "/ponto/{id}")
		public ResponseEntity<Ponto> getponto(@PathVariable("id") long id) {
			try {
				return ResponseEntity.ok(pontoService.buscar(id));
			} catch (EntidadeNaoEncontradaException e) {
				return ResponseEntity.notFound().build();
			} catch (Exception e) {
				
				e.printStackTrace();
				
				return ResponseEntity.status(500).build();
				
			}
		}


		// ------------------- add a entrada --------------------

		@PostMapping(value = "/ponto/novo")
		public ResponseEntity<Ponto> createponto(@RequestBody Ponto ponto) {
			System.out.println("Add Entrada ");

			pontoDAO.inserir(ponto);

			return new ResponseEntity<Ponto>(HttpStatus.CREATED);
		}
		
		@PutMapping(value = "/ponto/alterar/{id}")
		public ResponseEntity<Ponto> updatePonto(@PathVariable("id") long id, @RequestBody Ponto ponto) {
			System.out.println("Updating Entrada: " + id);

			Ponto pontoBuscado = pontoDAO.buscar(id);

			if (ponto == null) {
				System.out.println("ponto with id " + id + " not found");
				return new ResponseEntity<Ponto>(HttpStatus.NOT_FOUND);
			}

			pontoBuscado.setHorario(ponto.getHorario());
			pontoBuscado.setDia(ponto.getDia());
			pontoBuscado.setUsuario(ponto.getUsuario());
			
			pontoDAO.alterar(pontoBuscado);
			return new ResponseEntity<Ponto>(ponto, HttpStatus.OK);
		}

	}
