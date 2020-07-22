package com.wspfeiffer.apttestgradle.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Thing {
    @Id
    private Long id;

    @Column
    private String thingName;

    @Column String thingDesc;
}
