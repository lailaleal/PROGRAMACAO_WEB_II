package br.edu.iftm.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iftm.springapp.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
