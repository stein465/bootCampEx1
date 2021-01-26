package com.stein.exerciciobootcamp1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stein.exerciciobootcamp1.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{

}
