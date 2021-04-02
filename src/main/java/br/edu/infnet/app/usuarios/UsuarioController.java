
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
@RequestMapping(path = {"/usuarios"})
public class UsuarioController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @GetMapping
    public Iterable<Usuario> listarUsuarios() {
        
        return usuarioRepository.findAll();
    } 

    @GetMapping(path = {"/{id}"})
    public ResponseEntity obterPorId(@PathVariable int id) {
        
        ResponseEntity retorno = ResponseEntity.notFound().build();
        Usuario usuario = this.findById(id);
        if (usuario != null) {
            
            retorno = ResponseEntity.ok().body(usuario);
            
        }
        return retorno;  
    }  
    @GetMapping(path = {"/email/{email}"})
    public ResponseEntity obterPorEmail(@PathVariable String email) {
            
        ResponseEntity retorno = ResponseEntity.notFound().build();
        try {
          
            Usuario usuario = usuarioRepository.findByEmail(email);  
            if (usuario != null) {
        
                retorno = ResponseEntity.ok().body(usuario);    
            }
            
        } catch (Exception e)   {
        } 
        return retorno;
    }
    @PostMapping 
    public ResponseEntity inserirUsuario(@RequestBody Usuario usuario) {
        
        ResponseEntity retorno = ResponseEntity.badRequest().build();
        if(usuario != null && usuario.getId() == null) {
        
            
            Usuario usuarioInserido = usuarioRepository.save(usuario);
            retorno = ResponseEntity.ok().body(usuarioInserido);
        }
        return retorno;
    }

    @PutMapping 
    public ResponseEntity atualizarUsuario(@RequestBody Usuario usuario) {
    
        ResponseEntity retorno = ResponseEntity.badRequest().build();
        if (usuario != null && usuario.getId() != null ) {
          
            Usuario usuarioGravado = this.findById(usuario.getId());
            if (usuarioGravado != null) {
            
                try {
                    
                    retorno = ResponseEntity.ok().body(usuarioRepository.save(usuario));
                } catch (Exception e) {
                }                    
            }
        } 
        return retorno;
    }
       
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity excluir(@PathVariable int id){
        
        
        ResponseEntity retorno = ResponseEntity.notFound().build();
        Usuario usuario = this.findById(id);
        if (usuario != null) {
            
            usuarioRepository.deleteById(id);
            retorno = ResponseEntity.ok().build();
        }
        return retorno;
    }
           
    private Usuario findById(int id) {
        
        Usuario retorno = null;
        try {
            
            retorno = usuarioRepository.findById(id).get();  
        } catch (Exception e) {
        }
        return retorno;
    }
}
