package com.backend.application.controller;

import com.backend.application.dto.ContatoDTO;
import com.backend.application.service.ContatoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contatos")
public class ContatoController {

    private final ContatoService contatoService;

    public ContatoController(ContatoService contatoService) {
        this.contatoService = contatoService;
    }

    @PostMapping
    public ResponseEntity<ContatoDTO> criarContato(@RequestBody ContatoDTO contatoDTO) {
        ContatoDTO novoContato = contatoService.criarContato(contatoDTO);
        return ResponseEntity.ok(novoContato);
    }

    @GetMapping
    public ResponseEntity<List<ContatoDTO>> listarContatos() {
        List<ContatoDTO> contatos = contatoService.listarContatos();
        return ResponseEntity.ok(contatos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContatoDTO> buscarPorId(@PathVariable Long id) {
        ContatoDTO contato = contatoService.buscarPorId(id);
        return ResponseEntity.ok(contato);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContatoDTO> atualizarContato(@PathVariable Long id, @RequestBody ContatoDTO contatoDTO) {
        ContatoDTO contatoAtualizado = contatoService.atualizarContato(id, contatoDTO);
        return ResponseEntity.ok(contatoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarContato(@PathVariable Long id) {
        contatoService.deletarContato(id);
        return ResponseEntity.noContent().build();
    }
}
