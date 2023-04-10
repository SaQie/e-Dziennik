package pl.edziennik.eDziennik.domain.schoollevel.dto;

public record SchoolLevelRequestApiDto(
        Long id,
        String name
) {

    public static final String ID = "id";
    public static final String NAME = "name";


}
