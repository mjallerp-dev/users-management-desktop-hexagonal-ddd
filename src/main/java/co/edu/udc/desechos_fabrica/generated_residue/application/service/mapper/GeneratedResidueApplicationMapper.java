package co.edu.udc.desechos_fabrica.generated_residue.application.service.mapper;


import co.edu.udc.desechos_fabrica.enterprise.domain.valueobject.EnterpriseId;
import co.edu.udc.desechos_fabrica.generated_residue.application.service.dto.command.RegisterGeneratedResidueCommand;
import co.edu.udc.desechos_fabrica.generated_residue.domain.model.GeneratedResidueModel;
import co.edu.udc.desechos_fabrica.generated_residue.domain.valueobject.*;
import co.edu.udc.desechos_fabrica.residue.domain.valueobject.ResidueId;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GeneratedResidueApplicationMapper {

    public GeneratedResidueModel fromRegisterCommandToModel(RegisterGeneratedResidueCommand command) {
        return GeneratedResidueModel.create(
                null,
                new GeneratedResidueCode(command.code()),
                new ResidueId(command.residueId()),
                new EnterpriseId(command.enterpriseId()),
                new GeneratedQuantity(command.residueQuantity())
        );
    }
}
