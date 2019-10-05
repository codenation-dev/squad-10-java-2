package br.com.codenation.log.service.implementations;

import br.com.codenation.log.entity.Usuario;
import br.com.codenation.log.repository.UsuarioRepository;
import br.com.codenation.log.service.interfaces.UsuarioServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService implements UsuarioServiceInterface {

    private UsuarioRepository repository;

    @Override
    public Optional<Usuario> buscaPorEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    public Usuario salva(Usuario usuario) {
        return repository.save(usuario);
    }
}
