package co.edu.udc.desechos_fabrica.residue.application.service.dto.mapper;

import co.edu.udc.desechos_fabrica.residue.application.service.dto.command.RegisterResidueCommand;
import co.edu.udc.desechos_fabrica.residue.application.service.dto.command.UpdateResidueCommand;
import co.edu.udc.desechos_fabrica.residue.domain.enums.ResidueType;
import co.edu.udc.desechos_fabrica.residue.domain.model.ResidueModel;
import co.edu.udc.desechos_fabrica.residue.domain.valueobject.*;
import co.edu.udc.desechos_fabrica.shared.domain.enums.MeasurementUnit;
import lombok.experimental.UtilityClass;

import java.util.Collections;

@UtilityClass
public class ResidueApplicationMapper {

    public ResidueModel fromRegisterCommandToModel(RegisterResidueCommand command) {
        return ResidueModel.create(
                new ResidueName(command.name()),
                new MaxTransportTime(command.maxTransportTime()),
                new MaxTransportQuantity(command.maxTransportQuantity()),
                ResidueType.valueOf(command.residueType()),
                MeasurementUnit.valueOf(command.measurementUnit()),
                Collections.emptyList()
        );
    }

    public ResidueModel fromUpdateCommandToModel(ResidueModel current, UpdateResidueCommand command) {
        return current.updateWith(
                new ResidueName(command.name()),
                new MaxTransportQuantity(command.maxTransportQuantity()),
                new MaxTransportTime(command.maxTransportTime()),
                ResidueType.valueOf(command.residueType()),
                MeasurementUnit.valueOf(command.measurementUnit()),
                current.getChemicalComponents()
        );
    }
}
