package attendanceapp.common.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import attendanceapp.util.AttendanceAppUtils;

@Entity()
@Table(name = "schools", uniqueConstraints = { @UniqueConstraint(columnNames = "name") })
public class School implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3776706368364694697L;

	@Id()
	@Column()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank()
	@Length(max = 250)
	@Column(name = "name", unique = true, nullable = false, length = 250)
	private String name;

	@NotBlank()
	@Length(max = 250)
	@Column(name = "telephone", unique = true, nullable = false, length = 250)
	@Pattern(regexp = AttendanceAppUtils.TELEPHONE_VALIDATOR_REGEX)
	private String telephone;

	@NotBlank()
	@Length(max = 250)
	@Column(name = "email", unique = true, nullable = false, length = 250)
	@Pattern(regexp = AttendanceAppUtils.EMAIL_VALIDATOR_REGEX)
	private String email;

	@JsonIgnore()
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private Set<Student> students = new HashSet<Student>(0);

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	@Override()
	public String toString() {
		return "id=" + id + ", name=" + name + ", telephone=" + telephone + ", email=" + email;
	}

}
