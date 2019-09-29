package br.com.codenation.log.service.implementations;

import br.com.codenation.log.dto.LogDTO;
import br.com.codenation.log.endpoints.advice.LogNotFoundException;
import br.com.codenation.log.enums.Ambiente;
import br.com.codenation.log.enums.Nivel;
import br.com.codenation.log.enums.Ordenacao;
import br.com.codenation.log.service.interfaces.LogServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LogService implements LogServiceInterface {
    @Override
    public LogDTO buscarPorId(Ambiente ambiente, Long id) {
        // TODO: implementar metodo
        return Optional.of(new LogDTO()).orElseThrow(() -> new LogNotFoundException(id));
    }

    @Override
    public List<LogDTO> listarComFiltros(Ambiente ambiente, Nivel nivel, String descricao, String origem, Ordenacao ordenacao) {
        // TODO: implementar metodo
        return Collections.emptyList();
    }

    @Override
    public void apagar(Ambiente ambiente, Long id) {
        // TODO: implementar delete
    }

    @Override
    public void arquivar(Ambiente ambiente, Long id) {
        // TODO: implementar arquivar
    }
}
