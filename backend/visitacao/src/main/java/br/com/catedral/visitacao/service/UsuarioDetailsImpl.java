package br.com.catedral.visitacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.catedral.visitacao.model.Usuario;
import br.com.catedral.visitacao.repository.UsuarioRepository;

@Service
public class UsuarioDetailsImpl implements UserDetailsService {

	@Autowired
    private  UsuarioRepository usuarioRepository;
    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(username);
		if (usuario == null) {
			throw new RuntimeException();
		}
		return usuario;
	}
}

