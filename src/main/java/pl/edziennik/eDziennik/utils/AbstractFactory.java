package pl.edziennik.eDziennik.utils;

public interface AbstractFactory<T,S>{

    S from (T toMap);

    T to(S toMap);

}
