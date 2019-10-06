package br.com.codenation.log.endpoints;

import br.com.codenation.log.dto.LogDTO;
import br.com.codenation.log.enums.Ordenacao;
import br.com.codenation.log.service.implementations.LogService;
import br.com.codenation.message.dto.Ambiente;
import br.com.codenation.message.dto.Nivel;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/log")
@AllArgsConstructor
public class LogController {

    private LogService logService;

    @GetMapping("/ambiente/{ambiente}")
    @ApiOperation(value = "Lista logs aplicando filtros", produces = "application/json")
    public ResponseEntity<List<LogDTO>> listarComFiltros(@PathVariable Ambiente ambiente,
                                                         @RequestParam(required = false) Nivel nivel,
                                                         @RequestParam(required = false) String descricao,
                                                         @RequestParam(required = false) String origem,
                                                         @RequestParam(required = false) Ordenacao ordenacao
    ) {
        return new ResponseEntity<>(logService.listarComFiltros(ambiente, nivel, descricao, origem, ordenacao), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Busca log por id", produces = "application/json")
    public ResponseEntity<LogDTO> buscarPorId(@PathVariable Long id) {
        return new ResponseEntity<>(logService.buscarPorId(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Apaga log")
    public ResponseEntity apagar(@PathVariable Long id) {
        logService.apagar(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
