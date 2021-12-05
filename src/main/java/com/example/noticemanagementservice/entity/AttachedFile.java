package com.example.noticemanagementservice.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
/**
 * AttachedFile
 *
 * @author FPT Software
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Table(schema = "dbo", name = "attached_file")
public class AttachedFile extends Base implements Serializable {
    private static final long serialVersionUID = 344881855277285582L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "notice_id", referencedColumnName = "id")
    private Notice notice;
}
