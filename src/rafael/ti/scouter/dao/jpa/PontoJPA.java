package rafael.ti.scouter.dao.jpa;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import rafael.ti.scouter.dao.PontoDAO;
import rafael.ti.scouter.model.Ponto;

@Repository("pontoDao")
public class PontoJPA extends AbstractJPA<Ponto> implements PontoDAO{

	@Override
	public List<Ponto> buscarPorUsuario(String usuario) {
		List<Ponto> ponto = buscarPorCampos(new HashMap<String, Object>() {
			{
				put("usuario", usuario);
			}
		});
		
		if(ponto.isEmpty()) {
			return null;
		} else {
			return ponto;
		}
	}
				
	@Override
	public String getEntityName() {
		return "Ponto";
	}

}
