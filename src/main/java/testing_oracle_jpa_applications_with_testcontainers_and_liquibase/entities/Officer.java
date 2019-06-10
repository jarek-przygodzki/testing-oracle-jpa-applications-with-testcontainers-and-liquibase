package testing_oracle_jpa_applications_with_testcontainers_and_liquibase.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "OFFICERS")
public class Officer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="OFF_ID")
    private Long id;

    @Column(nullable = false, name = "OFF_NAME")
    private String name;

    @Column(name="OFF_HOME_PLANET")
    private String homePlanet;

    @ManyToOne
    @JoinColumn(name = "OFF_SHIP_ID")
    private Spaceship spaceship;

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

    public String getHomePlanet() {
        return homePlanet;
    }

    public void setHomePlanet(String homePlanet) {
        this.homePlanet = homePlanet;
    }

    public Spaceship getSpaceship() {
        return spaceship;
    }

    public void setSpaceship(Spaceship spaceship) {
        this.spaceship = spaceship;
    }

}
