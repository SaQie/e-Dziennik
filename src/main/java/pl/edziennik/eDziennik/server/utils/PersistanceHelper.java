package pl.edziennik.eDziennik.server.utils;


import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Optional;

public abstract class PersistanceHelper {

    private PersistanceHelper() {
    }

    public static Object getSingleResultOrNull(Query query){
        try {
            return query.getSingleResult();
        }catch (NoResultException result){
            return null;
        }
    }

    public static <T> Optional<T> getSingleResultAsOptional(Query query){
        try{
            return Optional.of((T) query.getSingleResult());
        }catch (NoResultException result){
            return Optional.empty();
        }
    }

    public static boolean isObjectExist(Query query){
        return query.getResultList().size() >= 1;
    }

}
