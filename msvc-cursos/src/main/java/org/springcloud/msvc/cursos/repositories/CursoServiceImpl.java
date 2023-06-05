package org.springcloud.msvc.cursos.repositories;

import org.springcloud.msvc.cursos.clients.UsuarioClientRest;
import org.springcloud.msvc.cursos.entity.Curso;
import org.springcloud.msvc.cursos.entity.CursoUsuario;
import org.springcloud.msvc.cursos.models.Usuario;
import org.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioClientRest usuarioClient;



    @Override
    @Transactional(readOnly = true)
    public List<Curso> listarCursos() {
        return (List<Curso>) cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> buscarCursoPorId(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    @Transactional
    public Curso guardarCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public void eliminarCurso(Long id) {
        cursoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void eliminarCursoUsuarioPorId(Long id) {
        cursoRepository.eliminarCursoUsuarioPorId(id);
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {

        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);

        if (cursoOptional.isPresent()){
            Usuario usuarioMsvc = usuarioClient.detalleUsuario(usuario.getId());

            Curso curso = cursoOptional.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso.addCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);

            return Optional.of(usuarioMsvc);
        }

        return Optional.empty();

    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {

        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);

        if (cursoOptional.isPresent()){
            Usuario usuarioCreado= usuarioClient.crearUsuario(usuario);

            Curso curso = cursoOptional.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioCreado.getId());

            curso.addCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);

            return Optional.of(usuarioCreado);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> desAsignarUsuario(Usuario usuario, Long cursoId) {

        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);

        if (cursoOptional.isPresent()){
            Usuario usuarioMsvc = usuarioClient.detalleUsuario(usuario.getId());

            Curso curso = cursoOptional.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso.removeCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);

            return Optional.of(usuarioMsvc);
        }

        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> buscarCursoPorIdConUsuarios(Long id) {
        Optional<Curso> cursoOptional = cursoRepository.findById(id);
        if (cursoOptional.isPresent()){
            Curso curso = cursoOptional.get();

            if(!curso.getCursoUsarios().isEmpty()){
                /*
                List<Long> listaIds = curso.getCursoUsarios()
                        .stream()
                        .map(cursoUsario -> cursoUsario.getUsuarioId())
                        .collect(Collectors.toList());
                */
                List<Long> listaIds = curso.getCursoUsarios()
                                                    .stream()
                                                    .map(CursoUsuario::getUsuarioId)
                                                    .toList();

                List<Usuario> usuarios = usuarioClient.obtenerAlumnosPorCurso(listaIds);
                curso.setUsuarios(usuarios);
            }
            return Optional.of(curso);
        }
        return Optional.empty();
    }




}
