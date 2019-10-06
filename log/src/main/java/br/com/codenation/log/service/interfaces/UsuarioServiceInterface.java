package br.com.codenation.log.service.interfaces;

import br.com.codenation.log.entity.Usuario;

import java.util.Optional;

public interface UsuarioServiceInterface {
    Optional<Usuario> buscaPorEmail(String email);
    Optional<Usuario> salvar(Usuario usuario);
}
