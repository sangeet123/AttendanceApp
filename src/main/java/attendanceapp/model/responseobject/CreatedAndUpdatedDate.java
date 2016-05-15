package attendanceapp.model.responseobject;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class CreatedAndUpdatedDate {

	@JsonProperty(value = "createdOn")
	protected LocalDateTime createdOn;

	@JsonProperty(value = "updatedOn")
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
