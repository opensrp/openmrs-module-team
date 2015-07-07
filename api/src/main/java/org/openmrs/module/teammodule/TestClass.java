/**
 * 
 */
package org.openmrs.module.teammodule;

import java.util.Date;
import java.util.Scanner;

import org.openmrs.module.ModuleMustStartException;
import org.openmrs.util.DatabaseUpdateException;
import org.openmrs.util.InputRequiredException;

/**
 * @author Muhammad Safwan
 *
 */
public class TestClass {

	public static void main(String[] args) throws ModuleMustStartException, DatabaseUpdateException, InputRequiredException {
		/*Properties props = OpenmrsUtil.getRuntimeProperties("openmrs");
		
		boolean usetest = true;
		
		if (usetest) {
			props.put("connection.username", "root");
			props.put("connection.password", "$vicious$");
			Context.startup("jdbc:mysql://localhost:3306/openmrs?autoReconnect=true", "root", "$vicious$", props);
		} 
		
		try {
			Context.openSession();
			Context.authenticate("admin", "Admin123");
			List<TeamMember> tm = Context.getService(TeamMemberService.class).getMember("sja");
			tm.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Context.closeSession();
		}*/
		
		Date date = new Date();
		
		
		Scanner input = new Scanner(System.in);
		int var1 = input.nextInt();
		int var2 = input.nextInt();
		if(var1 == var2){
			System.out.println("error1");
		} else{
			System.out.println(var2);
		}
		
	}
	
}
