package attendanceapp.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class CreatedAndUpdatedDate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1093485370335608931L;

	@Column(name = "createdOn", nullable = false)
	protected LocalDateTime createdOn;

	@Column(name = "updatedOn", nullable = false)
	protected LocalDateTime updatedOn;

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}
}
