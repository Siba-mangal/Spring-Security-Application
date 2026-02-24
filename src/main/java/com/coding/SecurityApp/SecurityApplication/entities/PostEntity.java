package com.coding.SecurityApp.SecurityApplication.entities;

import jakarta.persistence.*;
import lombok.*;
//import org.hibernate.envers.Audited;
//import org.hibernate.envers.NotAudited;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @ManyToOne
    private User author;
    
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

    @PrePersist
    void beforeSave() {

    }

    @PreUpdate
    void beforeUpdate() {

    }

    @PreRemove
    void beforeDelete() {

    }


}
