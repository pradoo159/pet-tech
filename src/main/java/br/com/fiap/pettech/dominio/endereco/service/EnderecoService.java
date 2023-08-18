package br.com.fiap.pettech.dominio.endereco.service;

import br.com.fiap.pettech.dominio.endereco.dto.EnderecoDTO;
import br.com.fiap.pettech.dominio.endereco.entity.Endereco;
import br.com.fiap.pettech.dominio.endereco.repository.IEnderecoRepository;
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
public class EnderecoService {

    private final IEnderecoRepository enderecoRepository;

    @Autowired
    public EnderecoService(IEnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @Transactional(readOnly = true)
    public Page<EnderecoDTO> findAll(PageRequest pageRequest) {
        var enderecos = enderecoRepository.findAll(pageRequest);
        return enderecos.map(EnderecoDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public EnderecoDTO findById(Long id) {
        var endereco = enderecoRepository.findById(id).orElseThrow(
                () -> new ConcurrentModificationException("Endereço não encontrado")
        );

        return EnderecoDTO.fromEntity(endereco);
    }

    @Transactional
    public EnderecoDTO save(EnderecoDTO dto) {
        Endereco endereco = EnderecoDTO.toEntity(dto);
        var enderecoSaved = enderecoRepository.save(endereco);
        return EnderecoDTO.fromEntity(enderecoSaved);
    }

    @Transactional
    public EnderecoDTO update(Long id, EnderecoDTO dto) {
        try {
            Endereco endereco = enderecoRepository.getReferenceById(id);
            EnderecoDTO.mapperDtoToEntity(dto, endereco);
            endereco = enderecoRepository.save(endereco);
            return EnderecoDTO.fromEntity(endereco);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Endereço não encontrado, id: " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            enderecoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de integridade");
        }
    }

}
