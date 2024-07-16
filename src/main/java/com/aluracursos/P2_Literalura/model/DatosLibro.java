package com.aluracursos.P2_Literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("languages") List<String> lenguajes,
        @JsonAlias("download_count") Integer descargas,
        @JsonAlias("authors") List<DatosAutor> autor
) {
    @Override
    public String toString() {
        return "DatosLibro{" +
                "titulo='" + titulo + '\'' +
                ", lenguajes=" + lenguajes +
                ", descargas=" + descargas +
                ", autor=" + autor +
                '}';
    }
}
