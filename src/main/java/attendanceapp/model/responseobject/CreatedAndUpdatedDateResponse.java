package attendanceapp.model.responseobject;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class CreatedAndUpdatedDateResponse {

	@JsonProperty(value = "createdOn")
	protected String createdOn;

	@JsonProperty(value = "updatedOn")
	protected String updatedOn;

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(final String createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(final String updatedOn) {
		this.updatedOn = updatedOn;
	}
}
