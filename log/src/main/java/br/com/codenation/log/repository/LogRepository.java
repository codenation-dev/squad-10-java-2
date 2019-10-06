package br.com.codenation.log.repository;

import br.com.codenation.log.entity.Log;
import br.com.codenation.log.projection.LogProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
            "WHERE id=:id", nativeQuery = true)
    Optional<LogProjection> findLogById(@Param("id") Long id);

    @Query(value = "SELECT " +
            "ambiente, " +
            "nivel, " +
            "cast (payload AS VARCHAR), " +
            "usuario_id AS usuarioId, " +
            "count(*) OVER (PARTITION BY descricao, nivel) AS frequencia " +
            "FROM log " +
            "WHERE ambiente=:ambiente", nativeQuery = true)
    List<LogProjection> findAllByAmbiente(@Param("ambiente") String ambiente);

    @Query(value = "SELECT " +
            "ambiente, " +
            "nivel, " +
            "cast (payload AS VARCHAR), " +
            "usuario_id AS usuarioId, " +
            "count(*) OVER (PARTITION BY descricao, nivel) AS frequencia " +
            "FROM log " +
            "WHERE ambiente=:ambiente AND nivel=:nivel", nativeQuery = true)
    List<LogProjection> findAllByAmbienteAndNivel(@Param("ambiente") String ambiente, @Param("nivel") String nivel);

    @Query(value = "SELECT " +
            "ambiente, " +
            "nivel, " +
            "cast (payload AS VARCHAR), " +
            "usuario_id AS usuarioId, " +
            "count(*) OVER (PARTITION BY descricao, nivel) AS frequencia " +
            "FROM log " +
            "WHERE ambiente =:ambiente AND descricao=:descricao", nativeQuery = true)
    List<LogProjection> findAllByAmbienteAndDescricao(@Param("ambiente") String ambiente, @Param("descricao") String decricao);

    @Query(value = "SELECT " +
            "ambiente, " +
            "nivel, " +
            "cast (payload AS VARCHAR), " +
            "usuario_id AS usuarioId, " +
            "count(*) OVER (PARTITION BY descricao, nivel) AS frequencia " +
            "FROM log " +
            "WHERE ambiente=:ambiente AND payload ->> 'origem'=:origem", nativeQuery = true)
    List<LogProjection> findAllByAmbienteAndOrigem(@Param("ambiente") String ambiente, @Param("origem") String origem);
}
