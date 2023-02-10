package pl.edziennik.eDziennik.server.utils;


import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Optional;

/**
 * Helper class for returning objects from query
 */
public abstract class PersistanceHelper {

    private PersistanceHelper() {
    }

    /**
     * Returns single object or null if query will return more than one row
     */
    public static Object getSingleResultOrNull(Query query){
        try {
            return query.getSingleResult();
        }catch (NoResultException result){
            return null;
        }
    }

    /**
     * Return optional of returned object
     * This method return empty optional if query returned more than one row
     */
    public static <T> Optional<T> getSingleResultAsOptional(Query query){
        try{
            return Optional.of((T) query.getSingleResult());
        }catch (NoResultException result){
            return Optional.empty();
        }
    }

    /**
     * Object from query exists = true
     * Object from query not exists = false
     */
    public static boolean isObjectExist(Query query){
        return query.getResultList().size() >= 1;
    }

}
