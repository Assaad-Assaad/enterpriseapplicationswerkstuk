package be.ehb.enterpriseapplications.werkstuk.repository;

import be.ehb.enterpriseapplications.werkstuk.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> { }
