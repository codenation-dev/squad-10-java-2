package br.com.codenation.log.service.implementations;

import br.com.codenation.log.dto.UsuarioDTO;
import br.com.codenation.log.dto.mappers.UsuarioMapper;
import br.com.codenation.log.entity.Usuario;
import br.com.codenation.log.enums.Perfil;
import br.com.codenation.log.repository.UsuarioRepository;
import br.com.codenation.log.service.interfaces.UsuarioServiceInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UsuarioService implements UsuarioServiceInterface, UserDetailsService {

    private UsuarioRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // # Implementacao retornando usuario em banco
/*

        Optional<Usuario> user = repository.findByEmail(email);
        return user.map(u -> {
            GrantedAuthority authority = new SimpleGrantedAuthority(u.getPerfil().name());
            return new User(u.getEmail(), u.getSenha(), List.of(authority));
        }).orElseThrow(() -> new UsernameNotFoundException(email));
*/


        // # Implementacao com usuario mockado. Depois de inserir um usuario no banco, substituir pela outra implementacao

        GrantedAuthority authority = new SimpleGrantedAuthority(Perfil.ADMIN.name());
        return new User("squad", passwordEncoder.encode("123"), List.of(authority));

    }

    @Override
    public Optional<Usuario> buscaPorEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<Usuario> salvar(UsuarioDTO usuarioDTO) {
        Usuario usuario = UsuarioMapper.toEntity(usuarioDTO);
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        return Optional.of(repository.save(usuario));
    }

    public List<Usuario> listar() {
        return repository.findAll();
    }

}
