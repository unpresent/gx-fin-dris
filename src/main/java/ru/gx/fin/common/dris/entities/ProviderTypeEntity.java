package ru.gx.fin.common.dris.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.gx.core.data.entity.AbstractEntityObject;

import javax.persistence.*;

/**
 * Провайдеры
 */
@Entity
@Table(schema = "Dris", name = "Providers_Types")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString
public class ProviderTypeEntity extends AbstractEntityObject {
    @Id
    @Column(name = "Id")
    private short id;

    /**
     * Родительский тип самого верхнего уровня
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "RootType_Id")
    private ProviderTypeEntity rootType;

    /**
     * Родительский тип провайдера
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "Parent_Id")
    private ProviderTypeEntity parent;

    /**
     * Код типа провайдера
     */
    @Column(name = "Code", length = 50, nullable = false)
    private String code;

    /**
     * Название типа провайдера
     */
    @Column(name = "Name", length = 250, nullable = false)
    private String name;
}
