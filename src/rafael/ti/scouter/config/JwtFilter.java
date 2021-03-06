package rafael.ti.scouter.config;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import rafael.ti.scouter.model.Usuario;
import rafael.ti.scouter.utils.JwtUtils;

@Component
public class JwtFilter extends GenericFilterBean implements Filter, Serializable {
	
	private static final long serialVersionUID = 4116097870624832661L;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String token = req.getHeader("Authorization");

		if (token != null) {

			if (token.matches("(Bearer) .*")) {

				token = token.split(" ")[1];

				try {
					Usuario usuarioToken = JwtUtils.obterUsuarioDoTokenAutenticacao(token);
					SecurityContextHolder.getContext().setAuthentication(usuarioToken);

				} catch (Exception e) {
					res.setStatus(401);
				}
			} else {
				System.err.println("Token n�o esta no formato Bearer");
			}
		} else {
			System.err.println("Authorization n�o informado");
		}

		chain.doFilter(request, response);
	}

}
