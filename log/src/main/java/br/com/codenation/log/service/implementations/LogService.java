package br.com.codenation.log.service.implementations;

import br.com.codenation.log.dto.LogDTO;
import br.com.codenation.log.enums.AmbienteEnum;
import br.com.codenation.log.enums.NivelEnum;
import br.com.codenation.log.enums.OrdenacaoEnum;
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
    public Optional<LogDTO> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<LogDTO> listaPorAmbiente(AmbienteEnum ambiente) {
        return Collections.emptyList();
    }

    @Override
    public List<LogDTO> listaComFiltros(AmbienteEnum ambiente, NivelEnum nivel, String descricao, String origem, OrdenacaoEnum ordenacao) {
        return Collections.emptyList();
    }

    @Override
    public void delete(Long id) {
// TODO: implementar delete
    }
}
