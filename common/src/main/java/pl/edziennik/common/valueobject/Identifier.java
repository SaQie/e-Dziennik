package pl.edziennik.common.valueobject;

import java.util.UUID;

/**
 * Basic interface for id domain value objects
 */
public interface Identifier extends ValueObject {

    UUID id();


}
