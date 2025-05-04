package br.com.catedral.visitacao.service;

import br.com.catedral.visitacao.dto.ClienteDTO;
import br.com.catedral.visitacao.dto.ClienteInserirDTO;
import br.com.catedral.visitacao.model.Cliente;
import br.com.catedral.visitacao.model.Usuario;
import br.com.catedral.visitacao.repository.ClienteRepository;
import br.com.catedral.visitacao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<ClienteDTO> inserirLista(List<ClienteInserirDTO> clientesInserirDTO) {
        List<ClienteDTO> clientesSalvos = new ArrayList<>();

        for (ClienteInserirDTO clienteInserirDTO : clientesInserirDTO) {
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(clienteInserirDTO.idUsuario());

            if (usuarioOptional.isPresent()) {
                Cliente cliente = clienteInserirDTO.toEntity(new Cliente());
                cliente.setUsuario(usuarioOptional.get());

                Cliente clienteSalvo = clienteRepository.save(cliente);
                clientesSalvos.add(ClienteDTO.toDto(clienteSalvo));
                
                
            }
        }

        return clientesSalvos;
    }

    public ClienteDTO inserir(ClienteInserirDTO clienteInserirDTO) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(clienteInserirDTO.idUsuario());

        if (usuarioOptional.isEmpty()) {
            return null;
        }

        Cliente cliente = clienteInserirDTO.toEntity(new Cliente());
        cliente.setUsuario(usuarioOptional.get());

        Cliente clienteSalvo = clienteRepository.save(cliente);
        return ClienteDTO.toDto(clienteSalvo);
    }

    public List<ClienteDTO> buscarTodos() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(ClienteDTO::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ClienteDTO> buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.map(ClienteDTO::toDto);
    }

    public Optional<ClienteDTO> atualizar(Long id, ClienteInserirDTO clienteInserirDTO) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();

            clienteInserirDTO.toEntity(cliente);

            Optional<Usuario> usuarioOptional = usuarioRepository.findById(clienteInserirDTO.idUsuario());
            usuarioOptional.ifPresent(cliente::setUsuario);

            Cliente clienteAtualizado = clienteRepository.save(cliente);
            return Optional.of(ClienteDTO.toDto(clienteAtualizado));
        }

        return Optional.empty();
    }

    public boolean excluir(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}