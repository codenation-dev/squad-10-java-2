package br.com.codenation.log.entity;

import br.com.codenation.log.entity.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Log> logs;
}
