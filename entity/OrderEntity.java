package entity;

import java.time.Instant;
import java.util.List;

public class OrderEntity {
    private int id;
    private UserEntity user;
    private List<ProductEntity> products;
    private OrderStatus status;
    private Instant createdAt;

    public OrderEntity(int id, UserEntity user, List<ProductEntity> products, OrderStatus status, Instant createdAt) {
        this.id = id;
        this.user = user;
        this.products = products;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", user=" + user +
                ", products=" + products +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
