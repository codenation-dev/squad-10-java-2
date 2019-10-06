package br.com.codenation.log.security;

import br.com.codenation.log.dto.UsuarioDTO;
import br.com.codenation.log.dto.mappers.UsuarioMapper;
import br.com.codenation.log.entity.Usuario;
import br.com.codenation.log.enums.Perfil;
import br.com.codenation.log.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UsuarioRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GrantedAuthority authority = new SimpleGrantedAuthority(Perfil.ADMIN.name());
        // TODO implementar buscar usuario no bd
        return new User("squad", passwordEncoder.encode("123"), List.of(authority));
    }

    public Usuario salvarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = UsuarioMapper.toEntity(usuarioDTO);
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        return repository.save(usuario);
    }
}