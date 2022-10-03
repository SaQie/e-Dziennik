package pl.edziennik.eDziennik.server.schoollevel;

public enum SchoolLevelEnum {

    PRIMARY_SCHOOL("Szkoła podstawowa", 1L),
    HIGH_SCHOOL("Szkoła średnia", 2L),
    UNIVERSITY("Studia", 3L);

    public Long id;
    public String name;

    SchoolLevelEnum(String name, Long id) {
        this.id = id;
        this.name = name;
    }
}
