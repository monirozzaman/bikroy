package com.spring.bikroy.bikroy.domain.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Setter
@Getter
@MappedSuperclass
public abstract class BasicEntity implements Serializable {
    @Id
    private String id;
}
