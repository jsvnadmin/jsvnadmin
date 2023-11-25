import java.io.File;

import org.apache.catalina.startup.Tomcat;

public class SvnAdmin {

	public static void main(String[] args) throws Exception {
		int port = 8080;
		String dir = null;
		
		//port
		if (args.length>=1) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (Exception e) {
				System.out.println("port error");
				return;
			}
		}
		//appBase
		if (args.length>=2) {
			dir = args[1];
		}else {
			dir = new File(SvnAdmin.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getParent();
		}
		
		System.out.println("port="+port);
		System.out.println("dir="+dir);
		
		Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.getHost().setAppBase(dir);
        tomcat.addWebapp("/svnadmin", dir+"/svnadmin.war");
        tomcat.start();
        tomcat.getServer().await();
		
	}

}
