package com.shrunity.Itfirm.Repository;

import com.shrunity.Itfirm.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    //Product : entity class name that represent a table in db , Long : primary key in db

}
