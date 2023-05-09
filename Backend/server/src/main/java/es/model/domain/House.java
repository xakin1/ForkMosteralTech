package es.model.domain;

import javax.persistence.*;

@Entity(name = "t_house")
@Table(name = "t_house")
@PrimaryKeyJoinColumn(name = "id")
public class House extends Product {}
