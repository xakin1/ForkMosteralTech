package es.model.domain;

import javax.persistence.*;

@Entity(name = "t_furniture")
@Table(name = "t_furniture")
@PrimaryKeyJoinColumn(name = "id")
public class Furniture extends Product {}
