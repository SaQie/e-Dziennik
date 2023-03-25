package pl.edziennik.eDziennik.server.basics.page;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Dto class for wrap Spring page object
 *
 * @param <T>
 */
@Getter
public class PageDto<T> {

    private final Integer pagesCount;
    private final Integer actualPage;

    private final Integer itemsOnPage;
    private final Long itemsTotalCount;

    private final List<T> content;


    private PageDto(Page<T> page){
        this.pagesCount = page.getTotalPages();
        this.actualPage = page.getNumber();
        this.itemsOnPage = page.getSize();
        this.itemsTotalCount = page.getTotalElements();
        this.content = page.getContent();
    }

    public static <R> PageDto<R> fromPage(Page<R> page){
        if (page == null){
            return new PageDto<>(Page.empty());
        }
        return new PageDto<>(page);
    }

}
