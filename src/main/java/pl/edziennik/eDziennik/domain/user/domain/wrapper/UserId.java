package pl.edziennik.eDziennik.domain.user.domain.wrapper;

public record UserId(
        Long id
) {
    public static UserId wrap(Long id) {
        return new UserId(id);
    }
}
