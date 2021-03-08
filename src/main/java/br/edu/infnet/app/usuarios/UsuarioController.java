package br.edu.infnet.app.usuarios;

import br.edu.infnet.domain.usuarios.Usuario;
import br.edu.infnet.infra.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path={"/usuarios"})
public class UsuarioController {
    
    @Autowired
    public UsuarioRepository usuarioRepository;
    
    @GetMapping
    public Iterable<Usuario> ListarUsurios(){
        return usuarioRepository.findAll();
    }
    
    @GetMapping(path = {"/{id}"})
    public ResponseEntity obterPorId(@PathVariable int id){
        
        ResponseEntity retorno = ResponseEntity.notFound().build();
        try{
           Usuario usuario = usuarioRepository.findById(id).get();
           
           if(usuario != null){
               retorno = ResponseEntity.ok().body(usuario);
           }
        }catch (Exception e){
        }
        
        return retorno;
    }
        
    @GetMapping(path = {"/email/{email}"})
    public ResponseEntity obterPorEmail(@PathVariable String email){
        
        ResponseEntity retorno = ResponseEntity.notFound().build();
        try{
           Usuario usuario = usuarioRepository.findByEmail(email);
           
           if(usuario != null){
               retorno = ResponseEntity.ok().body(usuario);
           }
        }catch (Exception e){
        }
        
        return retorno;
    }    
    
    @PostMapping
    public Usuario inserirUsuario(@RequestBody Usuario usuario){
        return usuarioRepository.save(usuario);
    }
    
    @PutMapping
    public ResponseEntity atualizarUsuario(@RequestBody Usuario usuario){
        ResponseEntity retorno = ResponseEntity.notFound().build();
        
        if(usuario != null && usuario.getId() != null){
            Usuario usuarioGravado = this.findById(usuario.getId());
        
            if(usuarioGravado != null){
                
                try{
                    retorno = ResponseEntity.ok().body(usuarioRepository.save(usuario));
                }catch (Exception e){
                }
                
            }   else{
                    retorno=ResponseEntity.badRequest().build();
            }
        }
        return retorno;
    }
    
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity excluir(@PathVariable int id){
        
        ResponseEntity retorno = ResponseEntity.notFound().build();
        Usuario usuario = usuarioRepository.findById(id).get();
        
        if(usuario != null){
            usuarioRepository.deleteById(id);
            retorno = ResponseEntity.ok().build();
           }
       
        return retorno;
    }
    
    private Usuario findById (int id){
        Usuario retorno = null;
        try{
            retorno = usuarioRepository.findById(id).get();
           }catch (Exception e){
           }
        return retorno;
    }
}