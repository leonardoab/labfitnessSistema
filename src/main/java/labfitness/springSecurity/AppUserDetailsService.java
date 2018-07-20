package labfitness.springSecurity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUserDetailsService implements UserDetailsService {
	
	public class UsuarioSistema extends User {

		private static final long serialVersionUID = 1L;	
		
		public UsuarioSistema( Collection<? extends GrantedAuthority> authorities) {
			super("Admin", "123", authorities);
			
		}
	}

	public UserDetails loadUserByUsername(String UsuarioRede)
			throws UsernameNotFoundException {

		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("TOTAL"));

		UsuarioSistema user = null;

		user = new UsuarioSistema(authorities);		

		return user;
	}	

}
