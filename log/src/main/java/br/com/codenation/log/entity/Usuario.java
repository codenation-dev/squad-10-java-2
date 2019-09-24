package br.com.codenation.log.entity;

import br.com.codenation.log.enums.Perfil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String nome;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Perfil perfil;

    @Email
    private String email;

    @NotNull
    @NotBlank
    private String senha;
}
