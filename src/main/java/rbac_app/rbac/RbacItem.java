package rbac_app.rbac;

import jakarta.persistence.*;
import rbac_app.role.Role;
import rbac_app.user.MyUser;

@Entity
@Table(name = "rbac_items")
public class RbacItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private MyUser user;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public RbacItem() {
    }

    public RbacItem(int id, MyUser user, Role role) {
        this.id = id;
        this.user = user;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RbacItem{" +
                "id=" + id +
                ", user=" + user +
                ", role=" + role +
                '}';
    }
}