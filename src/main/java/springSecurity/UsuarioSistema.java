package springSecurity;
import java.util.Collection;

import model.Colaborador;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;



public class UsuarioSistema extends User {

	private static final long serialVersionUID = 1L;
	
	private Colaborador colaborador;
	
	public UsuarioSistema(Colaborador colaborador, Collection<? extends GrantedAuthority> authorities) {
		super(colaborador.getUsuarioRede(), "123", authorities);
		this.colaborador = colaborador;
	}

	public Colaborador getUsuario() {
		return colaborador;
	}

}
