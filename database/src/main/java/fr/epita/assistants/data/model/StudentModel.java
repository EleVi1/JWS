package fr.epita.assistants.data.model;

import org.hibernate.annotations.Entity;
import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "students")
public class StudentModel {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY) // primary key
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "course_id")
    private Long course_id;
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private CourseModel course;
}
