package br.com.catedral.visitacao.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.catedral.visitacao.dto.UsuarioCpfDTO;
import br.com.catedral.visitacao.dto.UsuarioDTO;
import br.com.catedral.visitacao.dto.UsuarioInserirDTO;
import br.com.catedral.visitacao.exception.EmailException;
import br.com.catedral.visitacao.exception.SenhaException;
import br.com.catedral.visitacao.model.Perfil;
import br.com.catedral.visitacao.model.Usuario;
import br.com.catedral.visitacao.model.UsuarioPerfil;
import br.com.catedral.visitacao.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PerfilService perfilService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public List<UsuarioDTO> findAll() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		
		List<UsuarioDTO> usuariosDTO = usuarios.stream().map(UsuarioDTO::new).toList();
		return usuariosDTO;
	}
	
	public Optional<Usuario> buscar(Long id) {
		return usuarioRepository.findById(id);
	}
	
	@Transactional
	public UsuarioDTO inserir(UsuarioInserirDTO usuarioInserirDTO) throws EmailException, SenhaException {
	    if (!usuarioInserirDTO.getSenha().equals(usuarioInserirDTO.getConfirmaSenha())) {
	        throw new SenhaException("Senha e Confirma Senha não são iguais");
	    }
	    if (usuarioRepository.findByEmail(usuarioInserirDTO.getEmail()) != null) {
	        throw new EmailException("Email já existente");
	    }

	    Usuario usuario = new Usuario();
	    usuario.setNome(usuarioInserirDTO.getNome());
	    usuario.setEmail(usuarioInserirDTO.getEmail());
	    usuario.setSenha(encoder.encode(usuarioInserirDTO.getSenha()));

	    Set<UsuarioPerfil> perfis = new HashSet<>();
	    for (Perfil perfil : usuarioInserirDTO.getPerfis()) {
	        perfil = perfilService.buscar(perfil.getId());
	        UsuarioPerfil usuarioPerfil = new UsuarioPerfil(usuario, perfil, LocalDate.now());
	        perfis.add(usuarioPerfil);
	    }
	    usuario.setUsuarioPerfis(perfis);

	    usuario = usuarioRepository.save(usuario);

		try {
			emailService.emailCadastro(usuario);
		} catch (IOException e) {
			e.printStackTrace();
		}

	    UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
	    return usuarioDTO;
	}
	
	public UsuarioDTO inserirCpf(UsuarioCpfDTO usuarioInserirDTO, Long id) {
		 Optional<Usuario> usuarioOPT = usuarioRepository.findById(id);
		 
		 if(usuarioOPT.isPresent()) {
			 
			 Usuario usuario = usuarioOPT.get();
			 
			 usuario.setCpf(usuarioInserirDTO.getCpf());
			 
			 Usuario usuarioAtualizado = usuarioRepository.save(usuario);
			 
			 UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioAtualizado);
			 
			 return usuarioDTO;
		 }
		return null;
		
	}
	
}
