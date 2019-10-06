package br.com.codenation.log.dto.mappers;

import br.com.codenation.log.dto.UsuarioDTO;
import br.com.codenation.log.entity.Usuario;

public class UsuarioMapper {
    public static UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setPerfil(usuario.getPerfil());
        return usuarioDTO;
    }

    public static Usuario toEntity(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPerfil(usuarioDTO.getPerfil());
        return usuario;
    }
}
