/**
 *
 */
package mx.gob.imss.mscme.admonplazas.repository.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.Predicate;
import mx.gob.imss.mscme.admonplazas.models.entities.PlazaLayout;
import mx.gob.imss.mscme.admonplazas.models.request.PlazasFiltroRequest;

/**
 *
 */
@Component
public class PlazaLayoutSpecification {

	public Specification<PlazaLayout> busquedaPlazasFiltro(PlazasFiltroRequest filtro) {

		return (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<>();

			if (filtro.getCveOoad() != null) {
				predicates.add(cb.equal(root.get("cveOoad"), filtro.getCveOoad()));
			}

			if (filtro.getNumPlaza() != null) {
				predicates.add(cb.equal(root.get("numPlaza"), filtro.getNumPlaza()));
			}

			if (StringUtils.hasText(filtro.getOrigenPlaza())) {
				predicates.add(cb.equal(cb.upper(root.get("origenPlaza")), filtro.getOrigenPlaza().trim().toUpperCase()));
			}

			if (filtro.getIdConvocatoria() != null) {
				predicates.add(cb.equal(root.get("idConvocatoria"), filtro.getIdConvocatoria()));
			}

			if (filtro.getCveZona() != null) {
				predicates.add(cb.equal(root.get("cveZona"), filtro.getCveZona()));
			}

			if (StringUtils.hasText(filtro.getCveCategoria())) {
				predicates.add(cb.equal(root.get("cveCategoria"), filtro.getCveCategoria().trim()));
			}

			if (StringUtils.hasText(filtro.getCveEspecialidad())) {
				predicates.add(cb.equal(root.get("cveAreaResponsabilidad"), filtro.getCveEspecialidad().trim()));
			}

			if (StringUtils.hasText(filtro.getCveUnidad())) {
				predicates.add(cb.equal(root.get("cveUnidad"), filtro.getCveUnidad().trim()));
			}

			predicates.add(cb.equal(root.get("indActivo"), 1));
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}

}
