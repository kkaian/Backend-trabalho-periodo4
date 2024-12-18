package com.backend.application.service;

import com.backend.application.dto.ContatoDTO;
import com.backend.application.model.Contato;
import com.backend.application.model.Pessoa;
import com.backend.application.repository.ContatoRepository;
import com.backend.application.repository.PessoaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContatoService {

    private static final Logger logger = LoggerFactory.getLogger(ContatoService.class); 
    private final ContatoRepository contatoRepository;
    private final PessoaRepository pessoaRepository;

    public ContatoService(ContatoRepository contatoRepository, PessoaRepository pessoaRepository) {
        this.contatoRepository = contatoRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public ContatoDTO criarContato(ContatoDTO contatoDTO) {
        logger.info("Criando contato para pessoa com ID: {}", contatoDTO.getPessoaId()); 

        Pessoa pessoa = pessoaRepository.findById(contatoDTO.getPessoaId())
                .orElseThrow(() -> {
                    logger.error("Pessoa não encontrada com ID: {}", contatoDTO.getPessoaId()); 
                    return new RuntimeException("Pessoa não encontrada.");
                });
        
        Contato contato = new Contato();
        contato.setTipo(contatoDTO.getTipo());
        contato.setNumero(contatoDTO.getNumero());
        contato.setPessoa(pessoa);

        Contato contatoSalvo = contatoRepository.save(contato);
        logger.info("Contato criado com sucesso, ID: {}", contatoSalvo.getId()); 

        return new ContatoDTO(contatoSalvo.getId(), contatoSalvo.getTipo(), contatoSalvo.getNumero(),
                contatoSalvo.getPessoa().getId());
    }

    public List<ContatoDTO> listarContatos() {
        logger.info("Listando todos os contatos"); 

        return contatoRepository.findAll().stream()
                .map(contato -> new ContatoDTO(contato.getId(), contato.getTipo(), contato.getNumero(),
                        contato.getPessoa().getId()))
                .collect(Collectors.toList());
    }

    public ContatoDTO buscarPorId(Long id) {
        logger.info("Buscando contato com ID: {}", id); 

        Contato contato = contatoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Contato não encontrado com ID: {}", id); 
                    return new RuntimeException("Contato não encontrado.");
                });

        return new ContatoDTO(contato.getId(), contato.getTipo(), contato.getNumero(),
                contato.getPessoa().getId());
    }

    public ContatoDTO atualizarContato(Long id, ContatoDTO contatoDTO) {
        logger.info("Atualizando contato com ID: {}", id); 

        Contato contato = contatoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Contato não encontrado com ID: {}", id); 
                    return new RuntimeException("Contato não encontrado.");
                });

        contato.setTipo(contatoDTO.getTipo());
        contato.setNumero(contatoDTO.getNumero());
        Contato contatoAtualizado = contatoRepository.save(contato);

        logger.info("Contato atualizado com sucesso, ID: {}", contatoAtualizado.getId()); 

        return new ContatoDTO(contatoAtualizado.getId(), contatoAtualizado.getTipo(),
                contatoAtualizado.getNumero(), contatoAtualizado.getPessoa().getId());
    }

    public void deletarContato(Long id) {
        logger.info("Deletando contato com ID: {}", id); 

        Contato contato = contatoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Contato não encontrado com ID: {}", id); 
                    return new RuntimeException("Contato não encontrado.");
                });

        contatoRepository.delete(contato);

        logger.info("Contato com ID: {} deletado com sucesso", id); 
    }
}
