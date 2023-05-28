package pl.edziennik.infrastructure.strategy.file;

import pl.edziennik.common.enums.DocumentType;

public interface DocumentGeneratorStrategy<T> {

    byte[] generateDocument(T t);

    boolean forType(DocumentType documentType);

}
