package attendanceapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "subjects")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Subject extends CreatedAndUpdatedDate implements Serializable {

	private static final long serialVersionUID = 260641067862510958L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "short_name", unique = true, nullable = false, length = 250)
	private String shortName;

	@Column(name = "name", nullable = false, length = 250)
	private String name;

	@Column(name = "credit", length = 250)
	private int credit;

	@ManyToOne(fetch = FetchType.LAZY)
	private School school;

	public School getSchool() {
		return school;
	}

	public void setSchool(final School school) {
		this.school = school;
	}

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(final String shortName) {
		this.shortName = shortName;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(final int credit) {
		this.credit = credit;
	}

	@Override()
	public String toString() {
		return "Course [id=" + id + ", shortName=" + shortName + ", name=" + name + ", credit=" + credit + ", school="
				+ school + "]";
	}
}
