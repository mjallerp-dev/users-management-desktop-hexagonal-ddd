package co.edu.udc.desechos_fabrica.residue.application.port.out;

import co.edu.udc.desechos_fabrica.residue.domain.valueobject.ChemicalComponent;
import java.util.List;

public interface FindChemicalComponentsPort {
    List<ChemicalComponent> findAllComponentsByResidueId(Long residueId);
}
