package fr.epita.assistants.data.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course_model")
public class CourseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column @Size(max = 255)
    public String name;
    @ElementCollection
    @CollectionTable(name = "course_model_tags",
            joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "tag")
    public Set<String> course_tag = new HashSet<>();
}
