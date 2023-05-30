package org.springcloud.msvc.cursos.entity;

import org.springcloud.msvc.cursos.models.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curso_id")
    private List<CursoUsario> cursoUsarios;

    @Transient
    private List<Usuario> usuarios;



    public Curso() {
        cursoUsarios = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<CursoUsario> getCursoUsarios() {
        return cursoUsarios;
    }

    public void setCursoUsarios(List<CursoUsario> cursoUsarios) {
        this.cursoUsarios = cursoUsarios;
    }

    public void addCursoUsuario(CursoUsario cursoUsario){
        cursoUsarios.add(cursoUsario);
    }

    public void removeCursoUsuario(CursoUsario cursoUsario){
        cursoUsarios.remove(cursoUsario);
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
