package com.BasesDeDatos.demo.repository;

import com.BasesDeDatos.demo.model.Pensum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PensumRepository extends JpaRepository<Pensum, Long> {

    List<Pensum> findByProgramaId(Long programaId);
}
