package pl.edziennik.eDziennik.server.basics;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Function;

/**
 * Class that wrapping entity to pageable rest api response
 * @param <T>
 */
@Getter
@Setter
public class Page<T> {

    private Integer pagesCount;
    private Long itemsTotalCount;
    private Integer actualPage;
    private Integer itemsOnPage;

    private T entity;

    /**
     * Method maps wrapped page object to another
     */
    public <E> Page<E> map(Function<T, E> function){
        Page<E> page = new Page<>();
        page.setActualPage(this.actualPage);
        page.setItemsOnPage(this.itemsOnPage);
        page.setPagesCount(this.pagesCount);
        page.setItemsTotalCount(this.itemsTotalCount);
        E entity = function.apply(this.entity);
        page.setEntity(entity);
        return page;
    }


}
