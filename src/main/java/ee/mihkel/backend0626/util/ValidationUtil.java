package ee.mihkel.backend0626.util;

import ee.mihkel.backend0626.entity.Product;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidationUtil {

    public final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public void validateProduct(Product product) {
        if (product.getPrice() == null || product.getPrice() == 0) {
            throw new RuntimeException("Product must have a price");
        }
        if (product.getPrice() < 0) {
            throw new RuntimeException("Product must have a positive price");
        }
        if (product.getStock() == null || product.getStock() < 0) {
            throw new RuntimeException("Product shouldnt have negative stock");
        }
        if (product.getCategory() == null || product.getCategory().getId() == null) {
            throw new RuntimeException("Product must have category");
        }
    }

    public void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new RuntimeException("Email cannot be empty");
        }
        if (!VALID_EMAIL_ADDRESS_REGEX.matcher(email).matches()) {
            throw new RuntimeException("Email structure is not correct");
        }
    }

    public void validatePersonCode(String personCode) {
        if (personCode == null) {
            return;
        }
        if (personCode.length() != 11) {
            throw new RuntimeException("Personcode must be 11 symbols");
        }
    }
}
