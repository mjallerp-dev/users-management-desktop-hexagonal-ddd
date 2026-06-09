package co.edu.udc.desechos_fabrica.residue.application.port.in;

import co.edu.udc.desechos_fabrica.residue.domain.valueobject.ChemicalComponent;
import java.util.List;

public interface GetChemicalComponentsByResidueUseCase {
    List<ChemicalComponent> execute(Long residueId);
}
