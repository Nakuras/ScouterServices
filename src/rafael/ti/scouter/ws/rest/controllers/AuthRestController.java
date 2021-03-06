package rafael.ti.scouter.ws.rest.controllers;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTCreationException;

import rafael.ti.scouter.core.SessionUtils;
import rafael.ti.scouter.exceptions.EntidadeNaoEncontradaException;
import rafael.ti.scouter.exceptions.ValidacaoException;
import rafael.ti.scouter.model.Usuario;
import rafael.ti.scouter.services.UsuarioService;
import rafael.ti.scouter.utils.JwtUtils;
import rafael.ti.scouter.utils.MapUtils;

@RestController
@RequestMapping(value = "/rest/auth") // requisito padr�o
public class AuthRestController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private SessionUtils sessionUtils;

	// jwt � a ultima url pra acionar o m�todo
	@RequestMapping(value = "/jwt", method = { RequestMethod.POST })
	public ResponseEntity<Object> gerarJwt(@Valid @RequestBody Usuario usuario, BindingResult bindingResult)
			throws IllegalArgumentException, JWTCreationException, UnsupportedEncodingException {

		try {
			Usuario usuarioBuscado = usuarioService.buscarPorEmailESenha(usuario, bindingResult);
			Map<String, String> mapaToken = new HashMap<>();
			mapaToken.put("token", JwtUtils.gerarTokenAutenticacao(usuarioBuscado));
			sessionUtils.setUsuarioLogado(usuarioBuscado);
			return ResponseEntity.ok(mapaToken);		
			
		} catch (ValidacaoException e) {
			return ResponseEntity.unprocessableEntity().body(MapUtils.mapaDe(bindingResult));

		} catch (EntidadeNaoEncontradaException e) {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("X-Reason", "Credenciais inv�lidas").build();
		}
	}
}