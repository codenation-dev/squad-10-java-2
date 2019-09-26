package br.com.codenation.log.service.implementations;

import br.com.codenation.log.dto.LogDTO;
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
    public Optional<LogDTO> buscarPorId(Ambiente ambiente, Long id) {
        return Optional.empty();
    }

    @Override
    public List<LogDTO> listarComFiltros(Ambiente ambiente, Nivel nivel, String descricao, String origem, Ordenacao ordenacao) {
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
