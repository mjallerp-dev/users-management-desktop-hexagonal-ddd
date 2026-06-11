package co.edu.udc.desechos_fabrica.generated_residue.infrastructure.adapter.persistence.mapper;

import co.edu.udc.desechos_fabrica.generated_residue.domain.model.GeneratedResidueModel;
import co.edu.udc.desechos_fabrica.generated_residue.domain.valueobject.GeneratedQuantity;
import co.edu.udc.desechos_fabrica.generated_residue.domain.valueobject.GeneratedResidueCode;
import co.edu.udc.desechos_fabrica.generated_residue.infrastructure.adapter.persistence.dto.GeneratedResiduePersistenceDto;
import co.edu.udc.desechos_fabrica.residue.domain.valueobject.ResidueId;
import co.edu.udc.desechos_fabrica.enterprise.domain.valueobject.EnterpriseId;
import co.edu.udc.desechos_fabrica.shared.domain.enums.MeasurementUnit;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class GeneratedResiduePersistenceMapper {

    public static GeneratedResiduePersistenceDto fromModelToDto(final GeneratedResidueModel model) {
        return new GeneratedResiduePersistenceDto(
                model.getId(),
                model.getResidueId().value(),
                model.getEnterpriseId().value(),
                model.getCode().value(),
                model.getQuantity().value(),
                model.getQuantity().unit().getSymbol()
        );
    }

    public static GeneratedResidueModel fromResultSetToModel(final ResultSet resultSet) throws SQLException {
        Double quantityValue = resultSet.getDouble("generated_quantity");
        String unitString = resultSet.getString("quantity_unit");
        MeasurementUnit unit = MeasurementUnit.valueOf(unitString);
        Long enterpriseIdLong = getNullableLong(resultSet, "enterprise_id");
        EnterpriseId enterpriseId = enterpriseIdLong != null ? new EnterpriseId(enterpriseIdLong) : null;

        return new GeneratedResidueModel(
                getNullableLong(resultSet, "id"),
                new GeneratedResidueCode(resultSet.getString("code")),
                new ResidueId(resultSet.getLong("residue_id")),
                enterpriseId,
                new GeneratedQuantity(quantityValue, unit)
        );
    }

    public static List<GeneratedResidueModel> fromResultSetToModelList(final ResultSet resultSet) throws SQLException {
        final List<GeneratedResidueModel> list = new ArrayList<>();
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
