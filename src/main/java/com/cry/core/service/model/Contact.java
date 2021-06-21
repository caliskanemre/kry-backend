package com.cry.core.service.model;

import com.cry.core.domain.model.Condition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "contact_gen", sequenceName = "contact_id_seq", allocationSize = 1)
@Table
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_gen")
    private long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }
}
