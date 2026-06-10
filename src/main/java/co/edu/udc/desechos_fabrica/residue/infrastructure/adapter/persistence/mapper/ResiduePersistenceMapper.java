package co.edu.udc.desechos_fabrica.residue.infrastructure.adapter.persistence.mapper;

import co.edu.udc.desechos_fabrica.residue.domain.enums.ResidueType;
import co.edu.udc.desechos_fabrica.residue.domain.model.ResidueModel;
import co.edu.udc.desechos_fabrica.residue.domain.valueobject.ChemicalComponent;
import co.edu.udc.desechos_fabrica.residue.domain.valueobject.MaxTransportQuantity;
import co.edu.udc.desechos_fabrica.residue.domain.valueobject.MaxTransportTime;
import co.edu.udc.desechos_fabrica.residue.domain.valueobject.ResidueId;
import co.edu.udc.desechos_fabrica.residue.domain.valueobject.ResidueName;
import co.edu.udc.desechos_fabrica.residue.infrastructure.adapter.persistence.dto.ResiduePersistenceDto;
import co.edu.udc.desechos_fabrica.shared.domain.enums.MeasurementUnit;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ResiduePersistenceMapper {

    public ResiduePersistenceDto fromModelToDto(final ResidueModel model) {
        return new ResiduePersistenceDto(
                model.getId() != null ? model.getId().value() : null,
                model.getName().value(),
                model.getMaxTransportQuantity().value(),
                model.getMaxTransportTime().value(),
                model.getResidueType(),
                model.getMeasurementUnit(),
                model.getChemicalComponents().stream()
                        .map(ChemicalComponent::name)
                        .collect(Collectors.toList())
        );
    }

    public static ResidueModel fromResultSetToModel(final ResultSet resultSet) throws SQLException {
        return new ResidueModel(
                getNullableLong(resultSet, "id") != null ? new ResidueId(resultSet.getLong("id")) : null,
                new ResidueName(resultSet.getString("name")),
                new MaxTransportQuantity(resultSet.getDouble("max_transport_quantity")),
                new MaxTransportTime(resultSet.getInt("max_transport_time")),
                ResidueType.valueOf(resultSet.getString("residue_type")),
                MeasurementUnit.valueOf(resultSet.getString("measurement_unit")),
                Collections.emptyList() // Nota: Aquí deberías cargar los componentes si haces un JOIN
        );
    }

    public static List<ResidueModel> fromResultSetToModelList(final ResultSet resultSet) throws SQLException {
        final List<ResidueModel> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(fromResultSetToModel(resultSet));
        }
        return list;
    }

    private static Long getNullableLong(ResultSet resultSet, String column) throws SQLException {
        long value = resultSet.getLong(column);
        return resultSet.wasNull() ? null : value;
    }
}
