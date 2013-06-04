package cinema.database;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "USERROLES")
public class UserRoles implements Serializable {
	private static final long serialVersionUID = -7846215096792524127L;
	// @Id
	// @GeneratedValue (strategy = GenerationType.AUTO)
	// @Column (name = "ID")
	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long personID;
	@Column(name = "USERNAME")
	private String username;
	@Column(name = "ROLE")
	private String role;

	public UserRoles() {
	}

	public UserRoles(String username, String role) {

		this.username = username;
		this.role = role;

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getPersonID() {
		return personID;
	}

	public void setPersonID(long personID) {
		this.personID = personID;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
