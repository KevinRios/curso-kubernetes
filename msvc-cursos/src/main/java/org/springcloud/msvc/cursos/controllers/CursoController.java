package org.springcloud.msvc.cursos.controllers;

import org.springcloud.msvc.cursos.entity.Curso;
import org.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<?> crearCurso(@RequestBody Curso curso){
        Curso cursoGuardado = cursoService.guardarCurso(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarCurso(@RequestBody Curso curso, @PathVariable Long id){
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


}
