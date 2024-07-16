package com.aluracursos.P2_Literalura.repository;

import com.aluracursos.P2_Literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String nombreLibro);
    List<Libro> findByIdiomaEquals(String idioma);
}
