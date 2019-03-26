package rafael.ti.scouter.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import rafael.ti.scouter.core.LocalStorage;
import rafael.ti.scouter.core.SessionUtils;
import rafael.ti.scouter.dao.ClienteDAO;
import rafael.ti.scouter.model.Cliente;

@Controller
public class ClienteController {

	@Autowired
	private ClienteDAO clienteDao;

	@Autowired
	private SessionUtils sessionUtils;

	@Autowired
	private LocalStorage storage;

	@GetMapping("rest/cliente/editar")
	public String abrirEdicao(Model model, @RequestParam(name = "id", required = true) Long id,
			HttpServletResponse response) throws IOException {
		Cliente cliente = clienteDao.buscar(id);

		if (cliente == null) {
			response.sendError(404, "Cliente não encontrado");
			return null;
		}

		// Adicionando o modelo
		model.addAttribute("cliente", cliente);
		return "cliente/form";
	}

	@GetMapping("/app/adm/cliente")
	public String abrirLista(Model model) {

		List<Cliente> clientes = clienteDao.buscarTodos();

		model.addAttribute("clientes", clientes);

		return "usuario/lista";
	}

	@GetMapping("/app/adm/cliente/novo")
	public String abrirFormNovoCliente(Model model) {
		model.addAttribute("cliente", new Cliente());

		return "cliente/form";
	}

	@GetMapping("/app/adm/cliente/deletar")
	public String deletar(@RequestParam(required = true) Long id, HttpServletResponse response) throws IOException {
		Cliente clienteBuscado = clienteDao.buscar(id);

		if (clienteBuscado == null) {
			response.sendError(404, "O cliente não existe");
			return null;
		}

		clienteDao.deletar(clienteBuscado);
		return "redirect:/app/adm/cliente";
	}

	@PostMapping(value = { "/app/adm/cliente/salvar" }, consumes = { "multipart/form-data" })
	public String salvar(@Valid Cliente cliente, BindingResult brCliente,
			HttpServletRequest request) {

		// Se for um cadastro, valida qualquer campo...
		if (cliente.getId() == null && brCliente.hasFieldErrors()) {
			return "cliente/form";
		} else if (brCliente.hasFieldErrors("nome")) {
			return "cliente/form";
		}

		if (cliente.getId() == null) {
			
			clienteDao.inserir(cliente);

		} else {
			Cliente clienteBuscado = clienteDao.buscar(cliente.getId());
			BeanUtils.copyProperties(cliente, clienteBuscado, "id", "nome");
			System.out.println(clienteBuscado.getNome());
			System.out.println(clienteBuscado.getId());
			clienteDao.alterar(clienteBuscado);
		}

		return "redirect:/app/adm/cliente";
	}

}
