package co.edu.udc.desechos_fabrica.user.domain.valueobject;

import co.edu.udc.desechos_fabrica.user.domain.exception.InvalidUserEmailException;
import java.util.regex.Pattern;

public record UserEmail (String value){

  private static final Pattern EMAIL_PATTERN =
      Pattern.compile("^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$");

  public UserEmail {
    if (value == null) {
      throw new NullPointerException("UserEmail cannot be null");
    }
    value = value.trim().toLowerCase();

    validateNotEmpty(value);
    validateFormat(value);
  }

  public static UserEmail fromPlainText(final String value) {
    return new UserEmail(value);
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidUserEmailException.becauseValueIsEmpty();
    }
  }

  private static void validateFormat(final String normalizedValue) {
    if (!EMAIL_PATTERN.matcher(normalizedValue).matches()) {
      throw InvalidUserEmailException.becauseFormatIsInvalid(normalizedValue);
    }
  }

  @Override
  public String toString() {
    return value;
  }
}
