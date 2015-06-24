package fileserver.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "files", catalog = "file_server", uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class FilesEntity implements java.io.Serializable {

    private Integer id;
    private String name;
    private String hash;

    public FilesEntity() {
    }

    public FilesEntity(String name) {
        this.name = name;
    }

    public FilesEntity(String name, String hash) {
        this.name = name;
        this.hash = hash;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", unique = true, nullable = false, length = 100)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "hash")
    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

}
