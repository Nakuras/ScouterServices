package rafael.ti.scouter.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import rafael.ti.scouter.core.LocalStorage;
import rafael.ti.scouter.core.SessionUtils;
import rafael.ti.scouter.dao.UsuarioDAO;
import rafael.ti.scouter.model.Usuario;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioDAO usuarioDao;

	@Autowired
	private SessionUtils sessionUtils;
	
	@Autowired
	private LocalStorage storage;

	@GetMapping(value = { "/", "" })
	public String abrirLogin(Model model) {

		if (!sessionUtils.isUsuarioLogado()) {
			model.addAttribute("usuario", new Usuario());
			return "index";
		} else {
			return "redirect:/app";
		}
	}

	@GetMapping("rest/usuario/editar")
	public String abrirEdicao(Model model, @RequestParam(name = "id", required = true) Long id,
			HttpServletResponse response) throws IOException {
		Usuario usuario = usuarioDao.buscar(id);

		if (usuario == null) {
			response.sendError(404, "Usuário não encontrado");
			return null;
		}

		// Adicionando o modelo
		model.addAttribute("usuario", usuario);
		return "usuario/form";
	}

	@GetMapping("/app/adm/usuario")
	public String abrirLista(Model model) {

		List<Usuario> usuarios = usuarioDao.buscarTodos();
		
		storage.aplicarCaminhoFotoEmUsuarios(usuarios);

		model.addAttribute("usuarios", usuarios);

		return "usuario/lista";
	}

	@GetMapping("/app/adm/usuario/novo")
	public String abrirFormNovoUsuario(Model model) {
		model.addAttribute("usuario", new Usuario());

		return "usuario/form";
	}

	@GetMapping("/app/perfil")
	public String abrirFormEditarUsuarioLogado(Model model) {
		model.addAttribute("usuario", sessionUtils.getUsuarioLogado());

		return "usuario/form";
	}

	@GetMapping("/app/alterarSenha")
	public String abrirFormAlterarSenha(Model model) {
		model.addAttribute("usuario", sessionUtils.getUsuarioLogado());

		return "usuario/alterarSenha";
	}

	@GetMapping("/app/adm/usuario/deletar")
	public String deletar(@RequestParam(required = true) Long id, HttpServletResponse response) throws IOException {
		Usuario usuarioBuscado = usuarioDao.buscar(id);

		if (usuarioBuscado == null) {
			response.sendError(404, "O usuário não existe");
			return null;
		}

		usuarioDao.deletar(usuarioBuscado);
		return "redirect:/app/adm/usuario";
	}

	@PostMapping(value = { "/app/adm/usuario/salvar" }, consumes = { "multipart/form-data" })
	public String salvar(@Valid Usuario usuario, BindingResult brUsuario,
			@RequestParam(name = "isAdministrador", required = false) Boolean administrador,
			HttpServletRequest request) {

		// Se for um cadastro, valida qualquer campo...
		if (usuario.getId() == null && brUsuario.hasFieldErrors()) {
			return "usuario/form";
		} else if (brUsuario.hasFieldErrors("nome") || brUsuario.hasFieldErrors("email")) {
			return "usuario/form";
		}

		if (usuario.getId() == null) {
			// Hasheia a senha do usuário
			usuario.hashearSenha();

			usuarioDao.inserir(usuario);

		} else {
			Usuario usuarioBuscado = usuarioDao.buscar(usuario.getId());
			BeanUtils.copyProperties(usuario, usuarioBuscado, "id", "senha");
			System.out.println(usuarioBuscado.getSenha());
			System.out.println(usuarioBuscado.getId());
			usuarioDao.alterar(usuarioBuscado);
		}

		return "redirect:/app/adm/usuario";
	}

	@PostMapping({ "/usuario/autenticar" })
	public String autenticar(@Valid Usuario usuario, BindingResult brUsuario) {

		// Se houver erros no usuario, reabre o index
		if (brUsuario.hasFieldErrors("email") || brUsuario.hasFieldErrors("senha")) {
			return "index";
		}

		// hasheia a senha inserida para buscar no banco de dados
		usuario.hashearSenha();
		Usuario usuarioBuscado = usuarioDao.buscarPorEmailESenha(usuario.getEmail(), usuario.getSenha());
		if (usuarioBuscado == null) {
			brUsuario.addError(new FieldError("usuario", "email", "Email ou senha inválidos"));
			return "index";
		}

		// Aplica o usuário na sessão e abre a página inicial
		sessionUtils.setUsuarioLogado(usuarioBuscado);

		return "redirect:/app/";
	}

	@GetMapping({ "/sair" })
	public String logout() {
		sessionUtils.invalidarSessao();

		return "redirect:/";
	}
}