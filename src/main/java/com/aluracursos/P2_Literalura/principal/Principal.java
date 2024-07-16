package com.aluracursos.P2_Literalura.principal;

import com.aluracursos.P2_Literalura.model.Autor;
import com.aluracursos.P2_Literalura.model.DatosLibro;
import com.aluracursos.P2_Literalura.model.Libro;
import com.aluracursos.P2_Literalura.model.Resultados;
import com.aluracursos.P2_Literalura.repository.AutorRepository;
import com.aluracursos.P2_Literalura.repository.LibroRepository;
import com.aluracursos.P2_Literalura.service.ConsumoAPI;
import com.aluracursos.P2_Literalura.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosLibro> datosLibro = new ArrayList<>();
    private List<Libro>  libros;
    private LibroRepository repositorio;
    private Optional<Libro> libroBuscado;
    private List<Autor> autores;
    private AutorRepository repostorioActor;

    public Principal(LibroRepository repository, AutorRepository repositoryActor){ this.repositorio = repository; this.repostorioActor = repositoryActor;}

    public void MuestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro en Web
                    2 - Mostrar libros buscados
                    3 - Buscar libro por titulo (en base de datos)
                    4 - Mostrar lista de autores
                    5 - Listar autores vivos en determinado año
                    6 - Buscar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    mostrarLibrosBuscados();
                    break;
                case 3:
                    buscarLibrosPorTitulo();
                    break;
                case 4:
                    mostrarListaAutores();
                    break;
                case 5:
                    mostrarAutoresVivos();
                    break;
                case 6:
                    buscarLibrosIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicacion ");
                    break;
                default:
                    System.out.println("Opcion invalida ");
            }
        }
    }



    private DatosLibro getDatosLibro() {
        System.out.println("Escribe el nombre del libro que buscas: ");
        var nombreLibro = teclado.nextLine();
        System.out.println(URL_BASE + nombreLibro.replace(" ", "+")); //nota
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));
        System.out.println(json);
        Resultados resultados = conversor.obtenerDatos(json, Resultados.class);
        if (resultados != null && !resultados.resultado().isEmpty()) {
            DatosLibro datosLibro = resultados.resultado().getFirst();
            return datosLibro;
        } else {
            return null;
        }
    }
    //COMPLETA
    private void buscarLibroWeb() {
        DatosLibro datos = getDatosLibro();
        Libro libro = new Libro(datos);
        repositorio.save(libro); // Para trabajar con repositorio
        System.out.println(datos);

        datosLibro.add(datos);
    }

    private void mostrarLibrosBuscados(){
        libros = repositorio.findAll(); // Para trabajar con repositorio
        libros.forEach(System.out::println); // Para trabajar con repositorio
        datosLibro.forEach(System.out::println);
    }

    private void buscarLibrosPorTitulo(){
        System.out.println("Escribe el titulo del libro que quieres buscar: ");
        var tituloLibro = teclado.nextLine();
        libroBuscado = repositorio.findByTituloContainsIgnoreCase(tituloLibro);
        if(libroBuscado.isPresent()){
            System.out.println("El libro buscado es: " + libroBuscado.get());
        } else {
            System.out.println("Libro no encontrado");
        }

        //Optional<Libro> libro = libros
    }

    private void mostrarListaAutores() {
        autores = repostorioActor.findAll();
        autores.forEach(System.out::println);
    }

    private void mostrarAutoresVivos(){
        autores = repostorioActor.findAll(); //creo que se elimina
        System.out.println("Escribe el año en el que deseas buscar: ");
        var fecha = teclado.nextInt();
        List<Autor> autoresVivos = repostorioActor.findByFechaNacimientoLessThanEqualAndFechaMuerteGreaterThanEqual(fecha, fecha);
        System.out.println("En el año " + fecha + " existeron los siguientes: ");
        autoresVivos.forEach(System.out::println);
    }

    private void buscarLibrosIdioma(){
        System.out.println("""
                Escoge idioma a buscar
                es - Español
                en - Inglés
                """);
        var idioma = teclado.nextLine();
        libros = repositorio.findByIdiomaEquals(idioma);
        long cantidadLibros = libros.size();
        System.out.println("Existe un total de " + cantidadLibros + " libros en el idioma seleccionado"); //corregir texto singular/plural "libro" y libros"
        System.out.println("Los libros en este idioma son: ");
        libros.forEach(System.out::println);

    }
}
