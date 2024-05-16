package com.qs.frimake.workstream.config.jpa.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Persistable;
import org.springframework.data.util.ProxyUtils;

import java.io.Serializable;

@MappedSuperclass
@SuperBuilder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public abstract class CustomAbstractPersistable<pk extends Serializable>
        implements Persistable<pk> {

    @Id
    @GeneratedValue
    @Nullable
    private pk id;

    @Transient
    public boolean isNew() {
        return this.getId() == null;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!this.getClass().equals(ProxyUtils.getUserClass(obj))) {
            return false;
        }
        CustomAbstractPersistable<?> that = (CustomAbstractPersistable<?>) obj;

        return this.getId() != null && this.getId().equals(that.getId());

    }

    public int hashCode() {
        int hashCode = 17;
        hashCode += this.getId() == null ? 0 : this.getId().hashCode() * 31;
        return hashCode;
    }
}
