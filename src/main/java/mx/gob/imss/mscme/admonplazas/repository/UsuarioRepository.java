package mx.gob.imss.mscme.admonplazas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.gob.imss.mscme.admonplazas.models.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	// Puedes agregar metodos de busqueda personalizados si los necesitas
	Optional<Usuario> findByRefEmailAndIndActivo(String email, Long indActivo);

	Optional<Usuario> findByRefCurpAndRefEmailAndIndActivo(String curp, String email, Long indActivo);

	Optional<Usuario> findByIdUsuarioAndIndActivo(Long idUsuario, Long indActivo);


}
