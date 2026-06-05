package co.edu.udc.desechos_fabrica.location.domain.valueobject;

import co.edu.udc.desechos_fabrica.location.domain.exception.InvalidLocationIdException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;


public class LocationIdTest {

    @Test
    @DisplayName("Should create a valid LocationId")
    public void testValidLocationIdCreation() {
        final Long validId = 123L;
        final LocationId locationId = new LocationId(validId);
        assertEquals(validId, locationId.value());
    }

    @Test
    @DisplayName("Should throw exception when creating LocationId with null value")
    public void testNullLocationIdCreation() {
        assertThrows(InvalidLocationIdException.class, () -> new LocationId(null));
    }
}
