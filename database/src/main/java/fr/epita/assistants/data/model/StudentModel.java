package fr.epita.assistants.data.model;

import javax.persistence.*;

@Entity
@Table(name = "student_model")
public class StudentModel {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY) // primary key
    public Long id;

    public String name;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    public CourseModel course_model;
}
