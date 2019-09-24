package br.com.codenation.log.service.interfaces;

import br.com.codenation.log.dto.LogDTO;
import br.com.codenation.log.enums.AmbienteEnum;
import br.com.codenation.log.enums.NivelEnum;
import br.com.codenation.log.enums.OrdenacaoEnum;

import java.util.List;
import java.util.Optional;

public interface LogServiceInterface {

    Optional<LogDTO> findById(Long id);

    List<LogDTO> listaPorAmbiente(AmbienteEnum ambiente);

    List<LogDTO> listaComFiltros(AmbienteEnum ambiente, NivelEnum nivel, String descricao, String origem, OrdenacaoEnum ordenacao);

    void delete(Long id);
}
