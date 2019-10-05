package br.com.codenation.log.endpoints;

import br.com.codenation.log.dto.UsuarioDTO;
import br.com.codenation.log.dto.mappers.UsuarioMapper;
import br.com.codenation.log.security.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuario")
@AllArgsConstructor
public class UsuarioController {
    private UserService userService;

    @PostMapping
    @ApiOperation(value = "Cadastra novo usuario", consumes = "aplication/json", produces = "aplication/json")
    public ResponseEntity<UsuarioDTO> salvarUsuario(@Valid UsuarioDTO usuario) {
        return new ResponseEntity<>(UsuarioMapper.toDTO(userService.salvarUsuario(usuario)), HttpStatus.CREATED);
    }
}
