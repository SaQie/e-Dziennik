package pl.edziennik.eDziennik.domain.parent.domain.wrapper;

public record ParentId(
        Long id
) {
    public static ParentId wrap(Long id) {
        return new ParentId(id);
    }

}
