package es.model.domain;

import javax.persistence.*;

@Entity(name = "t_appliances")
@Table(name = "t_appliances")
@PrimaryKeyJoinColumn(name = "id")
public class Appliances extends Product {}
