package com.almeida.Ecommercenoite.services;

import com.almeida.Ecommercenoite.enums.UserTypeEnum;
import com.almeida.Ecommercenoite.models.ProdutoModel;
import com.almeida.Ecommercenoite.models.UsuarioModel;
import com.almeida.Ecommercenoite.repositories.ProdutoRepository;
import com.almeida.Ecommercenoite.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class UsuarioService {

    public UsuarioService() {
    }

    @Autowired
    public UsuarioRepository usuarioRepository;

    public UsuarioModel createUsuario(UsuarioModel usuarioModel) {
        return usuarioRepository.save(usuarioModel);
    }

    public List<UsuarioModel> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<UsuarioModel> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    public void deleteUsuarioById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public List<UsuarioModel> findUsuarioByNome(String usaername) {
        return usuarioRepository.findUsuarioByUsernameContains(usaername.toUpperCase(Locale.ROOT));
    }

    public Boolean login(UsuarioModel usuario) {
        List<UsuarioModel> usuarios = usuarioRepository.findUsuarioByUsernameContains(usuario.getUsername());
        if (usuarios.size() != 0) {
            if (usuarios.get(0).getUsername().equals(usuario.getPassword()) &&
                    usuarios.get(0).getPassword().equals(usuario.getPassword()) &&
                    usuarios.get(0).getUserType() == usuario.getUserType()) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
}
