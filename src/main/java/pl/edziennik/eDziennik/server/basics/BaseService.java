package pl.edziennik.eDziennik.server.basics;

import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.exceptions.NotFoundException;

import java.io.Serializable;
import java.util.List;

@Service
public abstract class BaseService<E> implements IBaseService<E> {

    private IBaseDao<E> dao;

    public BaseService() {
    }

    public BaseService(IBaseDao<E> dao) {
        this.dao = dao;
    }

    @Override
    public List<E> findAll() {
        return dao.findAll();
    }

    @Override
    public E saveOrUpdate(E entity) {
        return dao.saveOrUpdate(entity);
    }

    @Override
    public E find(Long id) {
        return dao.find(id).orElseThrow(() -> new NotFoundException(dao.getClazzName() + " not found !"));
    }

    @Override
    public void remove(E entity) {
        dao.remove(entity);
    }

}
