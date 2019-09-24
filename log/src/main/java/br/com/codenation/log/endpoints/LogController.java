package br.com.codenation.log.endpoints;

import br.com.codenation.log.dto.LogDTO;
import br.com.codenation.log.endpoints.advice.LogNotFoundException;
import br.com.codenation.log.enums.AmbienteEnum;
import br.com.codenation.log.enums.NivelEnum;
import br.com.codenation.log.enums.OrdenacaoEnum;
import br.com.codenation.log.service.implementations.LogService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/log/{ambiente}")
@AllArgsConstructor
public class LogController {

    private LogService logService;

    @GetMapping
    public ResponseEntity<List<LogDTO>> findAll(@PathVariable AmbienteEnum ambiente) {
        return new ResponseEntity<>(logService.listaPorAmbiente(ambiente), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<LogDTO>> listaComFiltros(@PathVariable AmbienteEnum ambiente,
                                                        @RequestParam(required = false) NivelEnum nivel,
                                                        @RequestParam(required = false) String descricao,
                                                        @RequestParam(required = false) String origem,
                                                        @RequestParam(required = false) OrdenacaoEnum ordenacao
    ) {
        return new ResponseEntity<>(logService.listaComFiltros(ambiente, nivel, descricao, origem, ordenacao), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(logService.findById(id).orElseThrow(() -> new LogNotFoundException(id)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        logService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
