package org.springcloud.msvc.cursos.controllers;

import feign.FeignException;
import org.springcloud.msvc.cursos.entity.Curso;
import org.springcloud.msvc.cursos.models.Usuario;
import org.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class CursoController {

    @Autowired
    private CursoService cursoService;


    @GetMapping
    public ResponseEntity<List<Curso>> listarCursos() {
        return ResponseEntity.ok(cursoService.listarCursos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalleCurso(@PathVariable Long id) {
        Optional<Curso> cursoOptional = cursoService.buscarCursoPorId(id);
        if (cursoOptional.isPresent()){
            return ResponseEntity.ok(cursoOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> crearCurso(@Valid @RequestBody Curso curso, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return validar(bindingResult);
        }
        Curso cursoGuardado = cursoService.guardarCurso(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarCurso(@Valid @RequestBody Curso curso, BindingResult bindingResult, @PathVariable Long id){
        if (bindingResult.hasErrors()){
            return validar(bindingResult);
        }
        Optional<Curso> cursoOptional = cursoService.buscarCursoPorId(id);
        if (cursoOptional.isPresent()){
            Curso cursoEdit = cursoOptional.get();
            cursoEdit.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardarCurso(cursoEdit));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCurso(@PathVariable Long id){
        Optional<Curso> cursoOptional = cursoService.buscarCursoPorId(id);
        if (cursoOptional.isPresent()){
            cursoService.eliminarCurso(cursoOptional.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult bindingResult) {
        Map<String, String> errores = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error ->{
            errores.put(error.getField(), "Campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }


    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> usuarioOptional ;

        try {
            usuarioOptional = cursoService.asignarUsuario(usuario, cursoId);
        }catch (FeignException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "no existe usuario por ID" +
                            "/ error en la comunicacion" + ex.getMessage()));
        }


        if (usuarioOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> usuarioOptional ;

        try {
            usuarioOptional = cursoService.crearUsuario(usuario, cursoId);
        }catch (FeignException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", " no se pudo crear el usuario" + ex.getMessage()));
        }


        if (usuarioOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioOptional.get());
        }

        return ResponseEntity.notFound().build();

    }


    @DeleteMapping("/desAsignar-usuario/{cursoId}")
    public ResponseEntity<?> desAsignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> usuarioOptional ;

        try {
            usuarioOptional = cursoService.desAsignarUsuario(usuario, cursoId);
        }catch (FeignException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "no existe usuario por ID" +
                            "/ error en la comunicacion" + ex.getMessage()));
        }


        if (usuarioOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();

    }


}
