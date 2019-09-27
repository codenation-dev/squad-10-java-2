package br.com.codenation.agent.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payload {

    @ApiModelProperty("Título do Evento")
    private String titulo;

    @ApiModelProperty("Descrição do Evento")
    private String descricao;

    @ApiModelProperty("Ip de Origem")
    private String origem;

    @ApiModelProperty("Data e hora da ocorrência")
    private LocalDateTime dataHora;

    @ApiModelProperty("Lista de Detalhes Ex.: Tipo do Evento, Stack Trace, etc.")
    private List<String> detalhes;
}
