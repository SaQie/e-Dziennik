package pl.edziennik.eDziennik.domain.parent.dao;

import pl.edziennik.eDziennik.domain.parent.domain.Parent;
import pl.edziennik.eDziennik.server.basics.dao.IBaseDao;

public interface ParentDao extends IBaseDao<Parent> {

    boolean isParentExistsByPesel(String pesel);
}
