package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Actor;


public interface ActorRepository extends JpaRepository<Actor, Integer>{

}
