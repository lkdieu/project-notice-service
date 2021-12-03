package com.example.notificationmanagementservice.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * User
 *
 * @author FPT Software
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Table(schema = "dbo", name = "users")
public class User extends Base implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String userName;

    @Column
    private String password;

    @Column
    private String gmail;

    @Column
    private Boolean isEnable;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notice> notices;

}
