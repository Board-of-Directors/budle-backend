package ru.nsu.fit.pak.budle.dao.establishment.restaurant;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "menu_category")
@Getter
public class MenuCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "parent_category_id")
    private Long parentCategoryId;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_category_id")
    private List<MenuCategory> childCategories;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private List<Product> products;
}
