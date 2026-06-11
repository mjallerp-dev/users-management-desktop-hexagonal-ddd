package co.edu.udc.desechos_fabrica.generated_residue.infrastructure.adapter.persistence.repository;

import co.edu.udc.desechos_fabrica.generated_residue.application.port.out.SaveGeneratedResiduePort;
import co.edu.udc.desechos_fabrica.generated_residue.domain.model.GeneratedResidueModel;
import co.edu.udc.desechos_fabrica.generated_residue.infrastructure.adapter.persistence.dto.GeneratedResiduePersistenceDto;
import co.edu.udc.desechos_fabrica.generated_residue.infrastructure.adapter.persistence.exception.GeneratedResiduePersistenceException;
import co.edu.udc.desechos_fabrica.generated_residue.infrastructure.adapter.persistence.mapper.GeneratedResiduePersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.sql.*;

@Log
@RequiredArgsConstructor
public final class GeneratedResidueRepositoryPostgresSQL implements SaveGeneratedResiduePort {

    private static final String SQL_INSERT =
            "INSERT INTO generated_residue(residue_id, enterprise_id, code, generated_quantity, generation_date, quantity_unit) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

    private final Connection connection;

    @Override
    public GeneratedResidueModel save(final GeneratedResidueModel generatedResidue) {
        final GeneratedResiduePersistenceDto dto = GeneratedResiduePersistenceMapper.fromModelToDto(generatedResidue);
        final Long generatedId = executeSave(dto);
        return generatedResidue.withId(generatedId);
    }

    private Long executeSave(final GeneratedResiduePersistenceDto dto) {
        try (final PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, dto.residueId());
            statement.setLong(2, dto.enterpriseId());
            statement.setString(3, dto.code());
            statement.setDouble(4, dto.generatedQuantity());
            statement.setString(6, dto.quantityUnit());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating generated residue failed, no rows affected.");
            }

            try (final ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating generated residue failed, no ID obtained.");
                }
            }
        } catch (final SQLException exception) {
            throw GeneratedResiduePersistenceException.becauseSaveFailed(exception);
        }
    }
}
