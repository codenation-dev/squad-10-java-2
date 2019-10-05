package br.com.codenation.log.security;

import br.com.codenation.log.dto.UsuarioDTO;
import br.com.codenation.log.dto.mappers.UsuarioMapper;
import br.com.codenation.log.entity.Usuario;
import br.com.codenation.log.enums.Perfil;
import br.com.codenation.log.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UsuarioRepository repository;

    public UserService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GrantedAuthority authority = new SimpleGrantedAuthority(Perfil.ADMIN.name());
        return new User("squad", new BCryptPasswordEncoder().encode("123"), List.of(authority));
    }

    public Usuario salvarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = UsuarioMapper.toEntity(usuarioDTO);
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuarioDTO.getSenha()));
        return repository.save(usuario);
    }
}