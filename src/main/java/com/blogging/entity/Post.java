package com.blogging.entity;

import lombok.*;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import javax.xml.stream.events.Comment;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "posts",
      uniqueConstraints = @UniqueConstraint(columnNames = {"title"})
)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "content", nullable = false)
    private String content;

    @OneToMany(cascade=CascadeType.ALL,mappedBy = "post")
    private List<Comments> comments;
    //list is used for many
    //cascade is used because if we do any changes in parent table we want to reflect that in child table
    //mapped by is used to tell that this table is mapped by post(comment table)
}
