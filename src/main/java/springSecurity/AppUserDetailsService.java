package springSecurity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import model.Colaborador;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import util.cdi.CDIServiceLocator;
import dao.ColaboradorDAOImpl;




public class AppUserDetailsService implements UserDetailsService {

	@Inject
	private ColaboradorDAOImpl colaboradorDAO = new ColaboradorDAOImpl();


	public UserDetails loadUserByUsername(String UsuarioRede)throws UsernameNotFoundException {
		Colaborador colaborador = CDIServiceLocator.getBean(Colaborador.class);
		colaborador = colaboradorDAO.buscarUsuarioRede(UsuarioRede);

		UsuarioSistema user = null;

		if (colaborador != null) {
			user = new UsuarioSistema(colaborador, getGrupos(colaborador));
		}

		return user;
	}

	private Collection<? extends GrantedAuthority> getGrupos(Colaborador colaborador) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();		


		authorities.add(new SimpleGrantedAuthority("TOTAL"));	



		return authorities;

	}









}
