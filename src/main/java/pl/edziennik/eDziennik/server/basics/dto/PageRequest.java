package pl.edziennik.eDziennik.server.basics.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Class uses for pagination
 */
@Getter
@Setter
@AllArgsConstructor
public class PageRequest {


    private Integer page;
    private Integer size;




}