/**
 * 
 */
package org.openmrs.module.teammodule;

import java.io.File;
import java.util.Properties;

import org.openmrs.api.context.Context;
import org.openmrs.module.ModuleMustStartException;
import org.openmrs.module.teammodule.api.TeamMemberLocationService;
import org.openmrs.module.teammodule.api.TeamMemberService;
import org.openmrs.util.DatabaseUpdateException;
import org.openmrs.util.InputRequiredException;
import org.openmrs.util.OpenmrsUtil;

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
			props.put("connection.password", "123456");
			Context.startup("jdbc:mysql://localhost:3306/openmrs?autoReconnect=true", "root", "123456", props);
		} 
		
		try {
			Context.openSession();
			Context.authenticate("admin", "Admin123");
			List<TeamMember> tm = Context.getService(TeamMemberService.class).getMember("John");
			tm.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Context.closeSession();
		}*/
		
		/*Date date = new Date();
		
		
		Scanner input = new Scanner(System.in);
		int var1 = input.nextInt();
		int var2 = input.nextInt();
		if(var1 == var2){
			System.out.println("error1");
		} else{
			System.out.println(var2);
		}*/
		
		/*String s = "C:\\Users\\Zohaib Masood\\AppData\\Roaming\\OpenMRS";//OpenmrsUtil.getApplicationDataDirectory();
		File propsFile = new File(s, "openmrs-runtime.properties");
		Properties props = new Properties();
		OpenmrsUtil.loadProperties(props, propsFile);
		Context.startup("jdbc:mysql://localhost:3306/openmrs?autoReconnect=true", "root", "123456", props);
		try {
		    Context.openSession();
		    Context.authenticate("admin", "Admin123");
		    TeamMember tm = Context.getService(TeamMemberService.class).getTeamMemberById(1);
			System.out.print(tm.getId());
		} catch (Exception e) { e.printStackTrace(); 
		} finally { Context.closeSession();
		}*/
		
		try {
			Properties props = OpenmrsUtil.getRuntimeProperties("openmrs");
			String url = (String) props.get("connection.url");
			String username = (String) props.get("connection.username");
			String password = (String) props.get("connection.password");
	
		
			System.out.println(url);
			System.out.println(username);
			System.out.println(password);
			
			Context.startup(url, username, password, props);
		    Context.openSession();
		    Context.authenticate("admin", "Admin123");
		    
		    TeamMemberLocation tm = Context.getService(TeamMemberLocationService.class).getTeamMemberLocation(1);
			System.out.print(tm.getId());
			
		} catch (Exception e) { e.printStackTrace(); 
		} finally { Context.closeSession();
		}
	}
	
}
