package ru.nsu.fit.pak.budle.dao.establishment.restaurant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

import java.util.List;

@Entity
@Table(name = "menu_category")
@Getter
@Setter
@Accessors(chain = true)
public class MenuCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "parent_category_id")
    private Long parentCategoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "establishment_id")
    private Establishment establishment;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_category_id")
    private List<MenuCategory> childCategories;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private List<Product> products;
}
