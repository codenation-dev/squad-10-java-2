package br.com.codenation.log.repository;

import br.com.codenation.log.entity.Log;
import br.com.codenation.log.entity.enums.Ambiente;
import br.com.codenation.log.entity.enums.Nivel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LogRepository extends JpaRepository<Log, Long> {

    Optional<Log> findByAmbienteAndId(Ambiente ambiente, Long id);

    List<Log> findAllByAmbiente(Ambiente ambiente, Sort sort);

    List<Log> findAllByAmbienteAndNivel(Ambiente ambiente, Nivel nivel, Sort sort);

    List<Log> findAllByAmbienteAndDescricao(Ambiente ambiente, String decricao, Sort sort);

    @Query(value="SELECT * FROM log WHERE ambiente=?1 AND payload ->> 'origem'= ?2 ORDER BY ?3", nativeQuery = true)
    List<Log> findAllByAmbienteAndOrigem(Ambiente ambiente, String origem, String ordenacao);

    void deleteByAmbienteAndId(Ambiente ambiente, Long id);
}
