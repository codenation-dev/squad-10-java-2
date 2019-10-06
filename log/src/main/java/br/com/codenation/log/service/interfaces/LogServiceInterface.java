package br.com.codenation.log.service.interfaces;

import br.com.codenation.log.dto.LogDTO;
import br.com.codenation.log.entity.Log;
import br.com.codenation.log.enums.Ordenacao;
import br.com.codenation.message.dto.Ambiente;
import br.com.codenation.message.dto.Evento;
import br.com.codenation.message.dto.Nivel;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Optional;

public interface LogServiceInterface {

    LogDTO buscarPorId(Long id);

    List<LogDTO> listarComFiltros(Ambiente ambiente, Nivel nivel, String descricao, String origem, Ordenacao ordenacao);

    void apagar(Long id);

    Optional<Log> salvar(Evento log) throws JsonProcessingException;
}
