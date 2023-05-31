package org.springcloud.msvc.cursos.entity;

import javax.persistence.*;

@Entity
@Table(name = "cursos_usuarios")
public class CursoUsario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", unique = true)
    private Long usuarioId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public boolean equals(Object obj) {
        if(this ==  obj){
            return true;
        }
        if (!(obj instanceof CursoUsario)){
            return false;
        }
        CursoUsario cursoUsario = (CursoUsario) obj;
        return this.usuarioId!=null && this.usuarioId.equals(cursoUsario.usuarioId );
    }
}
