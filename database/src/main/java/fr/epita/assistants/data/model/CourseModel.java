package fr.epita.assistants.data.model;

import org.hibernate.annotations.Entity;
import org.hibernate.type.StringNVarcharType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course_model")
public class CourseModel {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "name") // default 255
    public String name;

    //@OneToMany(mappedBy = "course")
    //private List<StudentModel> students = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "course_model_tags",
            joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "tag")
    private Set<String> course_tag = new HashSet<>();
}
