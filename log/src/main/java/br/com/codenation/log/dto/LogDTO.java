package br.com.codenation.log.dto;

import br.com.codenation.log.enums.AmbienteEnum;
import br.com.codenation.log.enums.NivelEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogDTO {
    private AmbienteEnum ambiente;
    private NivelEnum nivel;
    private PayloadDTO payload;
//    private Usuario usuario;
}
