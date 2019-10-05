package br.com.codenation.log.repository;

import br.com.codenation.log.entity.Log;
import br.com.codenation.log.projection.LogProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LogRepository extends JpaRepository<Log, Long> {

    @Query(value = "SELECT " +
            "ambiente, " +
            "nivel, " +
            "cast (payload AS VARCHAR), " +
            "usuario_id AS usuarioId, " +
            "count(*) OVER (PARTITION BY descricao, nivel) AS frequencia " +
            "FROM log " +
            "WHERE id=?1", nativeQuery = true)
    Optional<LogProjection> findLogById(Long id);

    @Query(value = "SELECT " +
            "ambiente, " +
            "nivel, " +
            "cast (payload AS VARCHAR), " +
            "usuario_id AS usuarioId, " +
            "count(*) OVER (PARTITION BY descricao, nivel) AS frequencia " +
            "FROM log " +
            "WHERE ambiente=?1", nativeQuery = true)
    List<LogProjection> findAllByAmbiente(String ambiente);

    @Query(value = "SELECT " +
            "ambiente, " +
            "nivel, " +
            "cast (payload AS VARCHAR), " +
            "usuario_id AS usuarioId, " +
            "count(*) OVER (PARTITION BY descricao, nivel) AS frequencia " +
            "FROM log " +
            "WHERE ambiente=?1 AND nivel=?2", nativeQuery = true)
    List<LogProjection> findAllByAmbienteAndNivel(String ambiente, String nivel);

    @Query(value = "SELECT " +
            "ambiente, " +
            "nivel, " +
            "cast (payload AS VARCHAR), " +
            "usuario_id AS usuarioId, " +
            "count(*) OVER (PARTITION BY descricao, nivel) AS frequencia " +
            "FROM log " +
            "WHERE ambiente =?1 AND descricao=?2", nativeQuery = true)
    List<LogProjection> findAllByAmbienteAndDescricao(String ambiente, String decricao);

    @Query(value = "SELECT " +
            "ambiente, " +
            "nivel, " +
            "cast (payload AS VARCHAR), " +
            "usuario_id AS usuarioId, " +
            "count(*) OVER (PARTITION BY descricao, nivel) AS frequencia " +
            "FROM log " +
            "WHERE ambiente=?1 AND payload ->> 'origem'=?2", nativeQuery = true)
    List<LogProjection> findAllByAmbienteAndOrigem(String ambiente, String origem);
}
