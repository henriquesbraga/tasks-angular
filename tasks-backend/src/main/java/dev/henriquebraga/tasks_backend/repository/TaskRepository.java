package dev.henriquebraga.tasks_backend.repository;

import dev.henriquebraga.tasks_backend.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

  // Exemplo: buscar tarefas por pessoa
  List<Task> findByPersonId(Long personId);

  @Query(value = """
    SELECT * FROM tb_task_tsk t
    JOIN tb_person_prs p ON p.id_person_prs = t.id_person_tsk
    WHERE (:numero IS NULL OR t.id_task_tsk = :numero)
    AND (:titulo IS NULL OR LOWER(t.title_task_tsk) LIKE LOWER(CONCAT('%', :titulo, '%'))
    OR LOWER(t.desc_task_tsk) LIKE LOWER(CONCAT('%', :titulo, '%')))
    AND (:responsavel IS NULL OR p.id_person_prs = :responsavel)
    AND (:situacao IS NULL OR t.status_task_tsk = :situacao)
  """, nativeQuery = true
  )
  List<Task> findByFilters(
          @Param("numero") Long numero,
          @Param("titulo") String titulo,
          @Param("responsavel") Long responsavel,
          @Param("situacao") String situacao
  );








}
