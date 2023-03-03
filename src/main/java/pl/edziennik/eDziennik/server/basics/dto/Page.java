package pl.edziennik.eDziennik.server.basics.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Function;

/**
 * Class that wrapping entity to pageable rest api response
 * @param <INPUT>
 */
@Getter
@Setter
public class Page<INPUT> {

    private Integer pagesCount;
    private Long itemsTotalCount;
    private Integer actualPage;
    private Integer itemsOnPage;

    private INPUT entity;

    /**
     * Method maps wrapped page object to another
     */
    public <OUTPUT> Page<OUTPUT> map(Function<INPUT, OUTPUT> function){
        Page<OUTPUT> page = new Page<>();
        page.setActualPage(this.actualPage);
        page.setItemsOnPage(this.itemsOnPage);
        page.setPagesCount(this.pagesCount);
        page.setItemsTotalCount(this.itemsTotalCount);
        OUTPUT entity = function.apply(this.entity);
        page.setEntity(entity);
        return page;
    }


}