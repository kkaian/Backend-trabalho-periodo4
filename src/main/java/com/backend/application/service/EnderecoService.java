package com.backend.application.service;

import com.backend.application.dto.EnderecoDTO;
import com.backend.application.model.Endereco;
import com.backend.application.model.Pessoa;
import com.backend.application.repository.EnderecoRepository;
import com.backend.application.repository.PessoaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    private static final Logger logger = LoggerFactory.getLogger(EnderecoService.class); // Logger para a classe
    private final EnderecoRepository enderecoRepository;
    private final PessoaRepository pessoaRepository;

    public EnderecoService(EnderecoRepository enderecoRepository, PessoaRepository pessoaRepository) {
        this.enderecoRepository = enderecoRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public EnderecoDTO criarEndereco(EnderecoDTO enderecoDTO) {
        logger.info("Criando endereço para pessoa com ID: {}", enderecoDTO.getPessoaId());

        Pessoa pessoa = pessoaRepository.findById(enderecoDTO.getPessoaId())
                .orElseThrow(() -> {
                    logger.error("Pessoa não encontrada com ID: {}", enderecoDTO.getPessoaId());
                    return new RuntimeException("Pessoa não encontrada.");
                });
        
        Endereco endereco = new Endereco();
        endereco.setRua(enderecoDTO.getRua());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setEstado(enderecoDTO.getEstado());
        endereco.setPessoa(pessoa);
        
        Endereco enderecoSalvo = enderecoRepository.save(endereco);
        logger.info("Endereço criado com sucesso, ID: {}", enderecoSalvo.getId());

        return new EnderecoDTO(enderecoSalvo.getId(), enderecoSalvo.getRua(), enderecoSalvo.getCidade(),
                enderecoSalvo.getEstado(), enderecoSalvo.getPessoa().getId());
    }

    public List<EnderecoDTO> listarEnderecos() {
        logger.info("Listando todos os endereços");

        return enderecoRepository.findAll().stream()
                .map(endereco -> new EnderecoDTO(endereco.getId(), endereco.getRua(), endereco.getCidade(),
                        endereco.getEstado(), endereco.getPessoa().getId()))
                .collect(Collectors.toList());
    }

    public EnderecoDTO buscarPorId(Long id) {
        logger.info("Buscando endereço com ID: {}", id);

        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Endereço não encontrado com ID: {}", id);
                    return new RuntimeException("Endereço não encontrado.");
                });

        return new EnderecoDTO(endereco.getId(), endereco.getRua(), endereco.getCidade(),
                endereco.getEstado(), endereco.getPessoa().getId());
    }

    public EnderecoDTO atualizarEndereco(Long id, EnderecoDTO enderecoDTO) {
        logger.info("Atualizando endereço com ID: {}", id);

        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Endereço não encontrado com ID: {}", id);
                    return new RuntimeException("Endereço não encontrado.");
                });

        endereco.setRua(enderecoDTO.getRua());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setEstado(enderecoDTO.getEstado());
        Endereco enderecoAtualizado = enderecoRepository.save(endereco);

        logger.info("Endereço atualizado com sucesso, ID: {}", enderecoAtualizado.getId());

        return new EnderecoDTO(enderecoAtualizado.getId(), enderecoAtualizado.getRua(),
                enderecoAtualizado.getCidade(), enderecoAtualizado.getEstado(),
                enderecoAtualizado.getPessoa().getId());
    }

    public void deletarEndereco(Long id) {
        logger.info("Deletando endereço com ID: {}", id);

        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Endereço não encontrado com ID: {}", id);
                    return new RuntimeException("Endereço não encontrado.");
                });

        enderecoRepository.delete(endereco);
        logger.info("Endereço com ID: {} deletado com sucesso", id);
    }

    // Método para buscar endereços por cidade
    public List<EnderecoDTO> buscarPorCidade(String cidade) {
        logger.info("Buscando endereços na cidade: {}", cidade);

        List<Endereco> enderecos = enderecoRepository.findByCidade(cidade);
        if (enderecos.isEmpty()) {
            logger.warn("Nenhum endereço encontrado na cidade: {}", cidade);
        }

        return enderecos.stream()
                .map(endereco -> new EnderecoDTO(endereco.getId(), endereco.getRua(), endereco.getCidade(),
                        endereco.getEstado(), endereco.getPessoa().getId()))
                .collect(Collectors.toList());
    }
}
