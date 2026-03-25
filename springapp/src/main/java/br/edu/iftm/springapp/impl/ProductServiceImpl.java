package br.edu.iftm.springapp.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.iftm.springapp.model.Product;
import br.edu.iftm.springapp.repository.ProductRepository;
import br.edu.iftm.springapp.service.ProductService;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List <Product> getAllProducts(){
        return productRepository.findAll();
    }

    @Override
    public void saveProduct(Product product){
        this.productRepository.save(product);
    }

    @Override
    public Product getProductById(long id) {
        Optional < Product > optional = productRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    @Override
    public void deleteProductById(long id) {
        this.productRepository.deleteById(id);
    }

}
