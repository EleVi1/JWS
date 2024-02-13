package fr.epita.assistants.data.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course_model")
public class CourseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    @ElementCollection
    @CollectionTable(name = "course_model_tags",
            joinColumns = @JoinColumn(name = "course_id"))
    public Set<String> course_tag = new HashSet<>();
}
