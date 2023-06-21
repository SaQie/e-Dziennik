package pl.edziennik.common.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

@Getter
public class PageDto<T> implements Serializable {

    private final Integer pagesCount;
    private final Integer actualPage;

    private final Integer itemsOnPage;
    private final Long itemsTotalCount;

    private final List<T> content;


    private PageDto(Page<T> page) {
        this.pagesCount = page.getTotalPages();
        this.actualPage = page.getNumber();
        this.itemsOnPage = page.getSize();
        this.itemsTotalCount = page.getTotalElements();
        this.content = page.getContent();
    }

    public static <R> PageDto<R> fromPage(Page<R> page) {
        if (page == null) {
            return new PageDto<>(Page.empty());
        }
        return new PageDto<>(page);
    }
}
