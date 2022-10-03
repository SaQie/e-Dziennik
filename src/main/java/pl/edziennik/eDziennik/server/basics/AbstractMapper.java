package pl.edziennik.eDziennik.server.basics;

public interface AbstractMapper<S extends AbstractDto,T extends BasicEntity>{

    S toDto(T entity);

    T toEntity(S dto);


}
