package testing_oracle_jpa_applications_with_testcontainers_and_liquibase.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SPACESHIPS")
public class Spaceship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SHIP_ID")
    private Long id;

    @Column(nullable = false, name="SHIP_NAME")
    private String name;

    @Column(name="SHIP_CLASS")
    private String className;

    @OneToMany(mappedBy = "spaceship", cascade = { CascadeType.ALL })
    private List<Officer> officers = new ArrayList<Officer>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Officer> getOfficers() {
        return officers;
    }

    public void setOfficers(List<Officer> officers) {
        this.officers = officers;
    }


}
