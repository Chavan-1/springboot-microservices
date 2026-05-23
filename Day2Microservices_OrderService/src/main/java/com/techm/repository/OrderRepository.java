package com.techm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techm.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
