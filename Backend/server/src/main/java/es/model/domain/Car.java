package es.model.domain;

import javax.persistence.*;

@Entity(name = "t_car")
@Table(name = "t_car")
@PrimaryKeyJoinColumn(name = "id")
public class Car extends Product {
}
