package com.magicpost.app.magicPost.event;

import com.magicpost.app.magicPost.address.Address;
import com.magicpost.app.magicPost.order.TransportOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
public class TrackingEvent {
    @Id
    private Long id;
    private LocalDateTime timestamp;
    private String message;
    @Embedded
    private Address location;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transport_order_id")
    private TransportOrder transportOrder;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        TrackingEvent that = (TrackingEvent) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
