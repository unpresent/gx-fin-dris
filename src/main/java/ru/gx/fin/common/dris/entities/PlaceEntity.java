package ru.gx.fin.common.dris.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import ru.gx.core.data.entity.AbstractEntityObject;

import javax.persistence.*;

/**
 * Площадка
 */
@Entity
@Table(schema = "Dris", name = "Places")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString
public class PlaceEntity extends AbstractEntityObject {
    /**
     * Идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private short id;

    /**
     * Код.
     */
    @Column(name = "Code", length = 50, nullable = false)
    private String code;

    /**
     * Наименование.
     */
    @Column(name = "Name", length = 100, nullable = false)
    private String name;
}
