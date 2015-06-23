package fileserver.beans;

import java.io.File;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@ManagedBean
@Stateless
public class DataAccess {

    private FilesEntity file = new FilesEntity(); 
    private String search_request;
    private Boolean wcards = false;
    private String message;

    public Boolean getWcards() {
        return wcards;
    }

    public void setWcards(Boolean wcards) {
        this.wcards = wcards;
    }

    public FilesEntity getFile(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        file = (FilesEntity) session.get(FilesEntity.class, id);
       // session.close();
        return file;
    }

    public List<FilesEntity> getAllFiles() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<FilesEntity> result = session.createCriteria(FilesEntity.class).list();
        if (!result.isEmpty()) {
            return result;
        } else {
            file.setHash("На сервере нет ниодного файла, воспользуйтесь загрузкой");
            result.add(file);
            return result;
        }

    }

    public void deleteFile(FilesEntity file) {

        // удаляем запись из БД
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(file);
        session.flush();
        session.getTransaction().commit();
        //session.close();
    // удаляем сам файл

        File del_file = new File("/resources/files/" + file.getName());
        del_file.delete();
    }

    public void deleteFileById(Integer id) {
        deleteFile(getFile(id));
    }

    public void setSearchRequest(String search_request) {
        this.search_request = search_request;
    }

    public String getSearchRequest() {

        return search_request;
    }

    public Boolean addNewFile(FilesEntity file_entity) {
    // проверяем перед записью нет ли файла с таким хэшем в БД

        Boolean flag_existe = fileWidthHashExiste(file_entity.getHash());

        if (!flag_existe) {

            //если нет, записываем в БД
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.saveOrUpdate(file_entity);
            session.flush();
            session.getTransaction().commit();
           // session.close();
            return true;
        } else {
            message = "Файл с таким хэшем уже существует";

            return false;
        }
    }

    public Boolean fileWidthHashExiste(String hash) {

//    String sql = "SELECT * FROM files WHERE hash ="+hash;
//    Session session = HibernateUtil.getSessionFactory().openSession();     
//SQLQuery query = session.createSQLQuery(sql);
//query.addEntity(FilesEntity.class);
//return  query.getFirstResult() != null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria cr = session.createCriteria(FilesEntity.class);
        cr.add(Restrictions.like("hash", hash));
        List<FilesEntity> result = cr.list();
        return result.iterator().hasNext();

    }

    public List<FilesEntity> findFileByName() {
        String text = search_request;
        String W;  // параметр вставляемый в SQL запрос для поиска с wildcards
        if (wcards == true) {
            W = "%";
        } else {
            W = "";
        }
        String sql = "SELECT * FROM files WHERE name LIKE '" + W + text + W + "' LIMIT 25";
        Session session = HibernateUtil.getSessionFactory().openSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<FilesEntity> result = query.list();

        if (result.isEmpty()) {
            file.setHash("Ничего не найдено. Используйте '%' для расширенного поиска");
            result.add(file);
            return result;
        } else {
            return result;
        }
    }

    public String getMessage() {
        return message;
    }

}
