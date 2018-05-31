package com.dsep.a.fanghongyutest;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity  
@Table(name="T_STUDENT")  
@SuppressWarnings("serial")  
public class Student implements Serializable {  
	public static String TEST() {return "20180110";};
    private Integer id;  
    private String name;  
    private TeacherA teacher;  
      
    @Id  
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "T_STUDENT_GEN")  
    @TableGenerator(name = "T_STUDENT_GEN", table = "TB_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "T_STUDENT_GEN", allocationSize = 1)  
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
           
    @ManyToOne(cascade=CascadeType.REFRESH,optional=false,fetch = FetchType.LAZY)  
    /**!外键!(这也表示为所在对象为“关系被维护端”)*/  
    @JoinColumn(name = "teacher_id")   
    public TeacherA getTeacher() {  
        return teacher;  
    }  
    public void setTeacher(TeacherA teacher) {  
        this.teacher = teacher;  
    }  
    @Override  
    public String toString() {  
        return "teacher [id=" + id + ", name=" + name + "]";  
    }  
}  
