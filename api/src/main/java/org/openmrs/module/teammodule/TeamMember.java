/**
 * 
 */
package org.openmrs.module.teammodule;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//import javax.persistence.ManyToOne;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Location;
import org.openmrs.Person;
import org.openmrs.module.teammodule.Team;


/**
 * @author Muhammad Safwan
 *
 */

public class TeamMember extends BaseOpenmrsData implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Person person;
	
	private Integer personId;

	private Integer teamId;
	
	private int teamMemberId;
	
	private Boolean isTeamLead;
	
	//private Boolean isRetired;
	
	private String identifier;
	
	//private String memberName;
	
	//private String memberFamilyName;
	
	//private String gender;
	
	private Date joinDate;
	
	private Date leaveDate;
	
	//private Date joinDateFrom;
	
	//private Date joinDateTo;
	
	private Team team;
	
	//private Location location;
	
	private Set<Location> location = new HashSet<Location>(0);
	
	private String uuid;
	
	//private Integer id;
	
	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public TeamMember(){
		
	}
	

	/**
	 * @param person
	 * @param teamId
	 * @param teamMemberId
	 * @param identifier
	 * @param memberName
	 * @param joinDate
	 * @param leaveDate
	 * @param team
	 */
	/*public TeamMember(int personId, int teamId, int teamMemberId, String identifier, String memberName, Date joinDate, Date leaveDate, Team team) {
		//super();
		this.personId = personId;
		this.teamId = teamId;
		this.teamMemberId = teamMemberId;
		this.identifier = identifier;
		this.memberName = memberName;
		this.joinDate = joinDate;
		this.leaveDate = leaveDate;
		this.team = team;
	}*/

	/*public TeamMember(Team teamId){
		this.teamId = team.getTeamId();
	}

	/*public Integer getTeamLeadId() {
		return teamLeadId;
	}

	public void setTeamLeadId(Integer teamLeadId) {
		this.teamLeadId = teamLeadId;
	}*/

	public Integer getId() {
		return teamMemberId;
	}

	public Person getPerson() {
		return person;
	}



	public void setPerson(Person person) {
		this.person = person;
	}

	//changed method
	/*public Team getTeamId(Team teamId) {
		return teamId;
	}*/

	/*public int getTeamId() {
		return teamId;
	}



	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
*/


	public int getTeamMemberId() {
		return teamMemberId;
	}



	public void setTeamMemberId(int teamMemberId) {
		this.teamMemberId = teamMemberId;
	}



	public String getIdentifier() {
		return identifier;
	}



	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}



	public Date getJoinDate() {
		return joinDate;
	}



	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}



	public Date getLeaveDate() {
		return leaveDate;
	}





	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}



	public void setId(Integer id) {
		this.teamMemberId = id;
	}



	/*public String getMemberName() {
		return memberName;
	}



	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}*/



	public Team getTeam() {
		return team;
	}



	public void setTeam(Team team) {
		this.team = team;
	}


	public Integer getTeamId() {
		return teamId;
	}


	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}


	public Integer getPersonId() {
		return personId;
	}


	public void setPersonId(Integer personId) {
		this.personId = personId;
	}


	/*public String getMemberFamilyName() {
		return memberFamilyName;
	}


	public void setMemberFamilyName(String memberFamilyName) {
		this.memberFamilyName = memberFamilyName;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}*/


	public Boolean getIsTeamLead() {
		return isTeamLead;
	}


	public void setIsTeamLead(Boolean isTeamLead) {
		this.isTeamLead = isTeamLead;
	}


	public Set<Location> getLocation() {
		return location;
	}


	public void setLocation(Set<Location> location) {
		this.location = location;
	}


	/*public Location getLocation() {
		return location;
	}


	public void setLocation(Location location) {
		this.location = location;
	}*/


	/*public Boolean getIsRetired() {
		return isRetired;
	}


	public void setIsRetired(Boolean isRetired) {
		this.isRetired = isRetired;
	}


	public Date getJoinDateFrom() {
		return joinDateFrom;
	}


	public void setJoinDateFrom(Date joinDateFrom) {
		this.joinDateFrom = joinDateFrom;
	}


	public Date getJoinDateTo() {
		return joinDateTo;
	}


	public void setJoinDateTo(Date joinDateTo) {
		this.joinDateTo = joinDateTo;
	}*/
	
}
