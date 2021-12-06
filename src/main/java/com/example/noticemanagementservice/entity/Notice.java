package com.example.noticemanagementservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Notice
 *
 * @author FPT Software
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Table(schema = "dbo", name = "notice")
public class Notice extends Base implements Serializable {

    private static final long serialVersionUID = 344881855277285582L;
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column
    private Integer viewNumber;

    @Column
    private Boolean isEnable;

    @JoinColumn(name = "notice_id", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AttachedFile> attachFiles;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
