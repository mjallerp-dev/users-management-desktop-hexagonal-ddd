package co.edu.udc.desechos_fabrica.residue.application.port.out;

import co.edu.udc.desechos_fabrica.residue.domain.model.ResidueModel;
import co.edu.udc.desechos_fabrica.residue.domain.valueobject.ResidueId;

import java.util.Optional;

public interface FindResidueByIdPort {
    Optional<ResidueModel> findById(ResidueId id);
}