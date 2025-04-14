package br.com.catedral.visitacao.service;

import br.com.catedral.visitacao.dto.GuiaInserirDTO;
import br.com.catedral.visitacao.dto.GuiaDTO;
import br.com.catedral.visitacao.model.Guia;
import br.com.catedral.visitacao.model.Usuario;
import br.com.catedral.visitacao.repository.GuiaRepository;
import br.com.catedral.visitacao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GuiaService {

    @Autowired
    private GuiaRepository guiaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public GuiaDTO inserir(GuiaInserirDTO guiaInserirDTO) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(guiaInserirDTO.idUsuario());

        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Usuario usuario = usuarioOptional.get();
        
        Guia guia = new Guia();
        guiaInserirDTO.toEntity(guia);
        guia.setUsuario(usuario);
        
        guia = guiaRepository.save(guia);
        return GuiaDTO.toDto(guia);
    }

    @Transactional
    public GuiaDTO atualizar(Long id, GuiaInserirDTO guiaInserirDTO) {
        Optional<Guia> guiaOptional = guiaRepository.findById(id);

        if (guiaOptional.isEmpty()) {
            throw new RuntimeException("Guia não encontrado");
        }

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(guiaInserirDTO.idUsuario());

        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Usuario usuario = usuarioOptional.get();

        Guia guia = guiaOptional.get();
        guiaInserirDTO.toEntity(guia);
        guia.setUsuario(usuario); 
        guia = guiaRepository.save(guia); 
        return GuiaDTO.toDto(guia); 
    }

    public GuiaDTO buscarPorId(Long id) {
        Optional<Guia> guiaOptional = guiaRepository.findById(id);

        if (guiaOptional.isEmpty()) {
            throw new RuntimeException("Guia não encontrado");
        }

        Guia guia = guiaOptional.get();
        return GuiaDTO.toDto(guia);
    }

    public List<GuiaDTO> listarTodos() {
        List<Guia> guias = guiaRepository.findAll();
        return guias.stream()
                .map(GuiaDTO::toDto)
                .collect(Collectors.toList()); 
    }

    @Transactional
    public void deletar(Long id) {
        Optional<Guia> guiaOptional = guiaRepository.findById(id);

        if (guiaOptional.isEmpty()) {
            throw new RuntimeException("Guia não encontrado");
        }

        guiaRepository.deleteById(id); 
    }
}
