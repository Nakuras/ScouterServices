package rafael.ti.scouter.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import rafael.ti.scouter.core.SessionUtils;

@Component
public class AutenticacaoPorSessaoInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private SessionUtils session;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		boolean necessitaAutenticacao = request.getRequestURI().contains("/app");
		if (necessitaAutenticacao) {
			
			if (!session.isUsuarioLogado()) {
				response.sendError(401);
				return false;
			}
			
			boolean necessitaSerAdm = request.getRequestURI().contains("/dono");
			if (necessitaSerAdm && (session.getUsuarioLogado().getEmail() != null)) {
				response.sendError(403);
				return false;
			}
		}
		
			
		// Se não passou nos filtros acima, libera acesso
		return true;
	}

}
