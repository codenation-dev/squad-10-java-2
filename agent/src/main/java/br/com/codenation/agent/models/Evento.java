package br.com.codenation.agent.models;

import br.com.codenation.agent.enums.Ambiente;
import br.com.codenation.agent.enums.Nivel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Evento {

    @ApiModelProperty(value = "Ambiente do Evento")
    private Ambiente ambiente;

    @ApiModelProperty(value = "Nível do Evento")
    private Nivel nivel;

    @ApiModelProperty(value = "Payload do Evento")
    private Payload payload;

}
