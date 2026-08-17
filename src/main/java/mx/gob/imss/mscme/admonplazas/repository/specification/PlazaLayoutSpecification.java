/**
 * 
 */
package mx.gob.imss.mscme.admonplazas.repository.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import jakarta.persistence.criteria.Predicate;
import mx.gob.imss.mscme.admonplazas.models.entities.PlazaLayout;

/**
 *
 */
@Component
public class PlazaLayoutSpecification {

	public Specification<PlazaLayout> generarSpecificationCveOoadNumPlaza(Long cveOoad, Integer numPlaza) {
		return generarSpecificationCveOoadNumPlazaOrigen(cveOoad, numPlaza, null);
	}

	public Specification<PlazaLayout> generarSpecificationCveOoadNumPlazaOrigen(Long cveOoad, Integer numPlaza,
			String origenPlaza) {

		return (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<>();

			if (cveOoad != null) {
				predicates.add(cb.equal(root.get("cveOoad"), cveOoad));
			}

			if (numPlaza != null) {
				predicates.add(cb.equal(root.get("numPlaza"), numPlaza));
			}

			if (origenPlaza != null && !origenPlaza.isBlank()) {
				predicates.add(cb.equal(cb.upper(root.get("origenPlaza")), origenPlaza.trim().toUpperCase()));
			}

			predicates.add(cb.equal(root.get("indActivo"), 1));
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}

}
