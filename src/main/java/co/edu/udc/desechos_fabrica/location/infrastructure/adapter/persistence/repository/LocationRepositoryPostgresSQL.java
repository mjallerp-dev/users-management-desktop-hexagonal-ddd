package co.edu.udc.desechos_fabrica.location.infrastructure.adapter.persistence.repository;

import co.edu.udc.desechos_fabrica.location.application.port.out.GetAllLocationPort;
import co.edu.udc.desechos_fabrica.location.application.port.out.GetLocationByIdPort;
import co.edu.udc.desechos_fabrica.location.application.port.out.SaveLocationPort;
import co.edu.udc.desechos_fabrica.location.application.port.out.UpdateLocationPort;
import co.edu.udc.desechos_fabrica.location.domain.exception.LocationNotFoundException;
import co.edu.udc.desechos_fabrica.location.domain.model.LocationModel;
import co.edu.udc.desechos_fabrica.location.domain.valueobject.LocationId;
import co.edu.udc.desechos_fabrica.location.infrastructure.adapter.persistence.dto.LocationPersistenceDto;
import co.edu.udc.desechos_fabrica.location.infrastructure.adapter.persistence.mapper.LocationPersistenceMapper;
import co.edu.udc.desechos_fabrica.location.infrastructure.adapter.persistence.exception.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

@Log
@RequiredArgsConstructor
public final class LocationRepositoryPostgresSQL implements
        SaveLocationPort,
        UpdateLocationPort,
        GetLocationByIdPort,
        GetAllLocationPort {

    private static final String SQL_INSERT =
            "INSERT INTO location(name, address, enterprise_id, country, state, city, latitude, longitude, status, created_at, updated_at) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";

    private static final String SQL_SELECT_BY_ID =
            "SELECT id, name, address, enterprise_id, country, state, city, latitude, longitude, status, created_at, updated_at "
                    + "FROM location "
                    + "WHERE id = ? LIMIT 1";

    private void executeSave(final LocationPersistenceDto dto) {
        try (final PreparedStatement statement = connection.prepareStatement((SQL_INSERT))) {
            statement.setString(1, dto.name());
            statement.setString(2, dto.address());
            statement.setLong(3, dto.enterpriseId());
            statement.setString(4, dto.country());
            statement.setString(5, dto.state());
            statement.setString(6, dto.city());
            statement.setDouble(7, dto.latitude());
            statement.setDouble(8, dto.longitude());
            statement.setString(9, dto.status());
            statement.executeUpdate();
        } catch (final SQLException exception) {
            throw PersistenceException.becauseSaveFailed(String.valueOf(dto.id()), exception);
        }
    }

    private final Connection connection;

    @Override
    public Optional<LocationModel> getById(final Long id) {
        try (final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setLong(1, id);
            final var resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(LocationPersistenceMapper.fromResultSetToModel(resultSet));
        } catch (final SQLException exception) {
            throw PersistenceException.becauseFindByIdFailed(String.valueOf(id),exception);
        }
    }

    private LocationModel findByIdOrFail(final LocationId id){
        return getById(id.value())
                .orElseThrow(() -> LocationNotFoundException.becauseIdWasNotFound(id.value()));
    }

}
