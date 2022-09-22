package pl.edziennik.eDziennik.utils;

public interface AbstractFactory <S,T>{

    S from(T toConvert);

    T to(S toConvert);

}
