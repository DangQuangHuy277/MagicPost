package com.magicpost.app.magicPost.actor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.magicpost.app.magicPost.point.entity.TransactionPoint;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Shipper{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Hidden
    private UUID id;

    @NotNull
    private String name;
    private String email;
    @NotNull
    private String phone;
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "transaction_point_id")
    @JsonIgnore
    private TransactionPoint transactionPoint;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Shipper shipper = (Shipper) o;
        return getId() != null && Objects.equals(getId(), shipper.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

