package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DAOServico extends JpaRepository<Servico, Long> {

    @Query(value = "select * from servico s where s.id_prestador_id = ?1 and s.excluido is not true", nativeQuery = true)
    List<Servico> getServicosPrestador(Long idPrestador);

    @Query(value = "select * from servico s where s.id = ?1 and s.excluido is not true", nativeQuery = true)
    Servico getServicoByID(Long id);

    @Query(value = "select * from servico s where id_prestador_id != ?1 and s.excluido is not true", nativeQuery = true)
    List<Servico> getAllServicos(Long id);

    @Query(value = "select * from servico s where s.id_categoria_id = ?1 and s.id_sub_categoria_id = ?2 and s.excluido is not true and s.id_prestador_id != ?3", nativeQuery = true)
    List<Servico> getServicosCategoria1(Long idCategoria, Long idSubCategoria, Long idPrestador);

    @Query(value = "select * from servico s where s.id_categoria_id = ?1 and s.excluido is not true and s.id_prestador_id != ?2", nativeQuery = true)
    List<Servico> getServicosCategoria2(Long idCategoria, Long idPrestador);

    /*@Query(value =
            "INSERT INTO public.servico (nome,obs_servico,valor,id_prestador_id,id_categoria_id,id_sub_categoria_id) " +
            "VALUES (?1,?2,?3,?4,?5,?6)", nativeQuery = true)
    Servico savar(String nome, String obsServico, Double valor, Long idPrestador, Long idCategoria, Long idSubcategoria);*/
}
