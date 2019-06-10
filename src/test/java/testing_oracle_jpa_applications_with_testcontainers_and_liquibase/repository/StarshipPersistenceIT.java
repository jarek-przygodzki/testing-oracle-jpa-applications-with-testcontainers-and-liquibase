package testing_oracle_jpa_applications_with_testcontainers_and_liquibase.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import testing_oracle_jpa_applications_with_testcontainers_and_liquibase.entities.Officer;
import testing_oracle_jpa_applications_with_testcontainers_and_liquibase.entities.Spaceship;

/**
 * Persistence tests - h2 database and autogenerated schema
 */
public class StarshipPersistenceIT {

    private EntityManager em;
    private EntityTransaction tx;

    @Before
    public void setUp() {
        em = Persistence.createEntityManagerFactory("integration")
                        .createEntityManager();
        tx = em.getTransaction();
    }

    @Test
    public void persistSpaceshipAndOfficers() {
        inTx(em -> {
            Spaceship ship = new Spaceship();
            ship.setName("Andromeda Ascendant");
            ship.setClassName("Glorious Heritage Class");
            Officer beka = new Officer();
            beka.setSpaceship(ship);
            beka.setName("Beka Valentine");
            ship.getOfficers()
                .add(beka);
            Officer dylan = new Officer();
            dylan.setName("Dylan Hunt");
            dylan.setSpaceship(ship);
            ship.getOfficers()
                .add(dylan);
            em.persist(ship);
        });

        List<Spaceship> shipsInDb = inTx(em -> {
            return em.createQuery(
                    "SELECT DISTINCT s from Spaceship s LEFT JOIN FETCH s.officers WHERE s.name = :name",
                    Spaceship.class)
                     .setParameter("name", "Andromeda Ascendant")
                     .getResultList();
        });
        assertThat(shipsInDb).extracting("name")
                             .containsExactly("Andromeda Ascendant");
        assertThat(shipsInDb.get(0)
                            .getOfficers()).extracting("name")
                                           .containsExactlyInAnyOrder("Beka Valentine",
                                                   "Dylan Hunt");
    }

    @Test
    public void persistOfficerWithoutShip() {
        inTx(em -> {
            Officer deanna = new Officer();
            deanna.setName("Deanna Troi");
            em.persist(deanna);
        });

        Officer off = inTx(em -> {
            return em.createQuery("SELECT o FROM Officer o where o.name = :name", Officer.class)
                     .setParameter("name", "Deanna Troi")
                     .getSingleResult();
        });
        assertThat(off.getName()).isEqualTo("Deanna Troi");
    }
    private void inTx(Consumer<EntityManager> action) {
        tx.begin();
        action.accept(em);
        tx.commit();
        em.clear();
    }

    private <T> T inTx(Function<EntityManager, T> action) {
        tx.begin();
        T result = action.apply(em);
        tx.commit();
        em.clear();
        return result;
    }
}