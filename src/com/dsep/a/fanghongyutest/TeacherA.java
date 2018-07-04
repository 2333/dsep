package com.dsep.a.fanghongyutest;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity  
@Table(name="T_TEACHERA")  
@SuppressWarnings("serial")  
public class TeacherA implements Serializable {  
    private Integer id;  
    private String name;  
    private Set<Student> students = new HashSet<Student>();  
    @Id  
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "T_TEACHERA_GEN")  
    @TableGenerator(name = "T_TEACHERA_GEN", table = "TB_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "T_TEACHERA_GEN", allocationSize = 1)  
    public Integer getId() {  
        return id;  
    }  
  
    public void setId(Integer id) {  
        this.id = id;  
    }  
  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
    /** lazy加载 */  
    @OneToMany(mappedBy="teacher",cascade = CascadeType.ALL, fetch = FetchType.LAZY)  
    @OrderBy(value = "id ASC")/**指明加载Students 时按id 的升序排序*/  
    public Set<Student> getStudents() {  
        return students;  
    }  
  
    public void setStudents(Set<Student> students) {  
        this.students = students;  
    }  
  
    @Override  
    public String toString() {  
        return "teacher [id=" + id + ", name=" + name + ", students="  
                + students + "]";  
    }  
}  
