package br.com.codenation.agent.endpoints;


import br.com.codenation.message.dto.Evento;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/evento")
public class AgentController {

    @PostMapping
    @ApiOperation(value = "Registra um evento no agent")
    @ResponseStatus(HttpStatus.OK)
    public void registrarEvento(@RequestBody Evento evento) {

    }
}
