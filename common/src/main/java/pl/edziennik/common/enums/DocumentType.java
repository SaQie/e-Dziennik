package pl.edziennik.common.enums;

import pl.edziennik.common.exception.InvalidParameterException;

/**
 *  Available document types for generating student's subject grades summary
 */
public enum DocumentType {

    PDF("PDF");

    private final String type;

    DocumentType(String type) {
        this.type = type;
    }

    public static DocumentType get(String type) {
        for (DocumentType docType : DocumentType.values()) {
            if (docType.type.equals(type)) {
                return docType;
            }
        }
        throw new InvalidParameterException("Document type " + type + " not found");
    }
}
