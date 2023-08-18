package br.com.fiap.pettech.dominio.pessoa.dto;

import br.com.fiap.pettech.dominio.pessoa.entity.Pessoa;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record PessoaDTO(
        Long id,
        @NotBlank(message = "O CPF não pode estar em branco.")
        @CPF(message = "CPF inválido.")
        String cpf,
        String nome,
        LocalDate nascimento,
        @Email(message = "O email deve estar em um formato válido.")
        String email
) {

    public static Pessoa toEntity(PessoaDTO dto) {
        return new Pessoa(dto);
    }

    public static PessoaDTO fromEntity(Pessoa pessoa) {
        return new PessoaDTO(pessoa.getId(),
                pessoa.getCpf(),
                pessoa.getNome(),
                pessoa.getNascimento(),
                pessoa.getEmail()
        );
    }

    public static Pessoa mapperDtoToEntity(PessoaDTO dto, Pessoa pessoa) {
        pessoa.setCpf(dto.cpf);
        pessoa.setId(dto.id);
        pessoa.setEmail(dto.email);
        pessoa.setNome(dto.nome);
        return pessoa;
    }

}
