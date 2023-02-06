package pl.edziennik.eDziennik.server.basics;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Function;

@Getter
@Setter
public class Page<T> {

    private Integer pagesCount;
    private Long itemsTotalCount;
    private Integer actualPage;
    private Integer itemsOnPage;

    private T entity;

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
