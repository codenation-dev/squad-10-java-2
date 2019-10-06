package br.com.codenation.log.dto;

import br.com.codenation.log.entity.Usuario;
import br.com.codenation.message.dto.Ambiente;
import br.com.codenation.message.dto.Nivel;
import br.com.codenation.message.dto.Payload;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogDTO {
    private Long id;
    private Ambiente ambiente;
    private Nivel nivel;
    private Payload payload;
    private Long frequencia;
    private Usuario usuario;
}
