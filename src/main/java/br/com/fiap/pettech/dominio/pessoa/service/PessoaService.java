package br.com.fiap.pettech.dominio.pessoa.service;

import br.com.fiap.pettech.dominio.pessoa.dto.PessoaDTO;
import br.com.fiap.pettech.dominio.pessoa.entity.Pessoa;
import br.com.fiap.pettech.dominio.pessoa.repository.IPessoaRepository;
import br.com.fiap.pettech.exception.service.ControllerNotFoundException;
import br.com.fiap.pettech.exception.service.DatabaseException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ConcurrentModificationException;

@Service
public class PessoaService {

    private final IPessoaRepository pessoaRepository;

    @Autowired
    public PessoaService(IPessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional(readOnly = true)
    public Page<PessoaDTO> findAll(PageRequest pageRequest) {
        var pessoas = pessoaRepository.findAll(pageRequest);
        return pessoas.map(PessoaDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public PessoaDTO findById(Long id) {
        var pessoa = pessoaRepository.findById(id).orElseThrow(
                () -> new ConcurrentModificationException("Pessoa não encontrada")
        );

        return PessoaDTO.fromEntity(pessoa);
    }

    @Transactional
    public PessoaDTO save(PessoaDTO dto) {
        Pessoa pessoa = PessoaDTO.toEntity(dto);
        var pessoaSaved = pessoaRepository.save(pessoa);
        return PessoaDTO.fromEntity(pessoaSaved);
    }

    @Transactional
    public PessoaDTO update(Long id, PessoaDTO dto) {
        try {
            Pessoa pessoa = pessoaRepository.getReferenceById(id);
            PessoaDTO.mapperDtoToEntity(dto, pessoa);
            pessoa = pessoaRepository.save(pessoa);
            return PessoaDTO.fromEntity(pessoa);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Pessoa não encontrada, id: " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            pessoaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de integridade");
        }
    }
    
}
