package org.springcloud.msvc.usuarios.services;

import org.springcloud.msvc.usuarios.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listarUsuarios();
    Optional<Usuario> buscarUsuarioPorId(Long id);
    Usuario guardarUsuario(Usuario usuario);
    void eliminarUsuario(Long id);

    Optional<Usuario> buscarPorEmail(String email);

    List<Usuario> listarPorIds(Iterable<Long> ids);
}
