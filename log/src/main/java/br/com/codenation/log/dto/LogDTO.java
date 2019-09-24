package br.com.codenation.log.dto;

import br.com.codenation.log.entity.Usuario;
import br.com.codenation.log.enums.Ambiente;
import br.com.codenation.log.enums.Nivel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogDTO {
    private Ambiente ambiente;
    private Nivel nivel;
    private PayloadDTO payload;
    private Usuario usuario;
}
