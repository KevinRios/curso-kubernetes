package org.springcloud.msvc.cursos.services;

import org.springcloud.msvc.cursos.entity.Curso;
import org.springcloud.msvc.cursos.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso> listarCursos();

    Optional<Curso> buscarCursoPorId(Long id);

    Curso guardarCurso(Curso curso);

    void eliminarCurso(Long id);

    Optional<Usuario> asignarUsuario(Usuario usuario, Long id);

    Optional<Usuario> crearUsuario(Usuario usuario, Long id);

    Optional<Usuario> desAsignarUsuario(Usuario usuario, Long cursoId);


}
