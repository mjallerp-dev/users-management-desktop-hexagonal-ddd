package co.edu.udc.desechos_fabrica.location.infrastructure.adapter.persistence.repository;

import co.edu.udc.desechos_fabrica.location.application.port.out.GetAllLocationsPort;
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

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Log
@RequiredArgsConstructor
public final class LocationRepositoryPostgresSQL
        implements SaveLocationPort,
        UpdateLocationPort,
        GetLocationByIdPort,
        GetAllLocationsPort {

    private static final String SQL_INSERT =
            "INSERT INTO location(name, address, enterprise_id, country, state, city, latitude, longitude, status, created_at, updated_at) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";

    private static final String SQL_UPDATE =
            "UPDATE location SET name = ?, address = ?, country = ?, state = ?, city = ?, latitude = ?, longitude = ?, status = ?, updated_at = NOW() "
                    + "WHERE id = ?";

    private static final String SQL_SELECT_BY_ID =
            "SELECT id, name, address, enterprise_id, country, state, city, latitude, longitude, status, created_at, updated_at "
                    + "FROM location "
                    + "WHERE id = ? LIMIT 1";

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM location";

    @Override
    public LocationModel save(final LocationModel location) {
        final LocationPersistenceDto dto = LocationPersistenceMapper.fromModelToDto(location);
        final Long generatedId = executeSave(dto);
        return findByIdOrFail(new LocationId(generatedId));
    }

    @Override
    public LocationModel update(final LocationId id, final LocationModel locationToUpdate) {
        this.findByIdOrFail(id);
        final LocationPersistenceDto dto = LocationPersistenceMapper.fromModelToDto(locationToUpdate);
        executeUpdate(id.value(), dto);
        return findByIdOrFail(id);
    }

    private final Connection connection;

    private Long executeSave(final LocationPersistenceDto dto) {
        try (final PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
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
            try (final ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
                throw new SQLException("No se pudo obtener el ID generado para la localización.");
            }
        } catch (final SQLException exception) {
            throw PersistenceException.becauseSaveFailed(String.valueOf(dto.id()), exception);
        }
    }

    private void executeUpdate( final Long id, final LocationPersistenceDto dto) {
        try (final PreparedStatement statement = connection.prepareStatement((SQL_UPDATE))) {
            statement.setString(1, dto.name());
            statement.setString(2, dto.address());
            statement.setString(3, dto.country());
            statement.setString(4, dto.state());
            statement.setString(5, dto.city());
            statement.setDouble(6, dto.latitude());
            statement.setDouble(7, dto.longitude());
            statement.setString(8, dto.status());
            statement.setLong(9, id);
            statement.executeUpdate();
        } catch (final SQLException exception) {
            throw PersistenceException.becauseUpdateFailed(String.valueOf(dto.id()), exception);
        }
    }

    @Override
    public Optional<LocationModel> getById(final Long id) {
        try (final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (final var resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }
                return Optional.of(LocationPersistenceMapper.fromResultSetToModel(resultSet));
            }
        } catch (final SQLException exception) {
            throw PersistenceException.becauseFindByIdFailed(String.valueOf(id),exception);
        }
    }

    @Override
    public List<LocationModel> getAll() {
        try (final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);
            final ResultSet resultSet = statement.executeQuery()) {
            return LocationPersistenceMapper.fromResultSetToModelList(resultSet);
        } catch (final SQLException exception) {
            throw PersistenceException.becauseFindAllFailed(exception);
        }
    }

    private LocationModel findByIdOrFail(final LocationId id){
        return getById(id.value())
                .orElseThrow(() -> LocationNotFoundException.becauseIdWasNotFound(id.value()));
    }

}
