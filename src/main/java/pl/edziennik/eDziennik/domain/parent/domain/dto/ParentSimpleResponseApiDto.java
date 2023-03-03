package pl.edziennik.eDziennik.domain.parent.domain.dto;

import lombok.Getter;

@Getter
public class ParentSimpleResponseApiDto {

    private final Long id;
    private final String fullName;

    public ParentSimpleResponseApiDto(Long id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }
}
