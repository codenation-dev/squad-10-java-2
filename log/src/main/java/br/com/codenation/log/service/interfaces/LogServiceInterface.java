package br.com.codenation.log.service.interfaces;

import br.com.codenation.log.dto.LogDTO;
import br.com.codenation.log.enums.Ambiente;
import br.com.codenation.log.enums.Nivel;
import br.com.codenation.log.enums.Ordenacao;

import java.util.List;

public interface LogServiceInterface {

    LogDTO buscarPorId(Long id);

    List<LogDTO> listarComFiltros(Ambiente ambiente, Nivel nivel, String descricao, String origem, Ordenacao ordenacao);

    void apagar(Long id);

    void arquivar(Long id);
}
