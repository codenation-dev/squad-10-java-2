package br.com.codenation.log.service.implementations;

import br.com.codenation.log.entity.Usuario;
import br.com.codenation.log.repository.UsuarioRepository;
import br.com.codenation.log.service.interfaces.UsuarioServiceInterface;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService implements UsuarioServiceInterface {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);
    private UsuarioRepository repository;

    @Override
    public Optional<Usuario> buscaPorEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<Usuario> salvar(Usuario usuario) {
        try {
            return Optional.of(repository.save(usuario));
        } catch (Exception e) {
            log.error("Erro ao salvar usuario", e);
            return Optional.empty();
        }
    }
}
