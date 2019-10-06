package br.com.codenation.log.endpoints;

import br.com.codenation.log.dto.UsuarioDTO;
import br.com.codenation.log.dto.mappers.UsuarioMapper;
import br.com.codenation.log.endpoints.advice.ErroAoSalvarUsuarioException;
import br.com.codenation.log.entity.Usuario;
import br.com.codenation.log.service.implementations.UsuarioService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuario")
@AllArgsConstructor
public class UsuarioController {

    private UsuarioService userService;

    @GetMapping()
    @ApiOperation(value = "Lista usuarios cadastrados", produces = "application/json")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<Usuario> usuarios = userService.listar();
        return new ResponseEntity<>(usuarios.stream().map(UsuarioMapper::toDTO).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Cadastra novo usuario", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UsuarioDTO> salvarUsuario(@Valid UsuarioDTO usuario) {
        Usuario usuarioSalvo = userService.salvar(usuario).orElseThrow(ErroAoSalvarUsuarioException::new);
        return new ResponseEntity<>(UsuarioMapper.toDTO(usuarioSalvo), HttpStatus.CREATED);
    }
}
