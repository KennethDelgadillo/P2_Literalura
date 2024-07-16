package com.aluracursos.P2_Literalura.repository;

import com.aluracursos.P2_Literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long>{
    List<Autor> findByFechaNacimientoLessThanEqualAndFechaMuerteGreaterThanEqual(Integer fecha, Integer fecha1);

}



