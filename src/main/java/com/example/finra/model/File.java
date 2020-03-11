package com.example.finra.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
@ToString
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class File implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private Long size;
    @Column
    @Lob
    private byte[] data;
    @Column
    private String contentType;
    @CreationTimestamp
    private Date date;
}
