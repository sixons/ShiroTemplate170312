package util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

/**
 *
 */
public class ShiroUtil {
    public static Subject login(String configFile,String username,String password){
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:"+configFile);
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try{
            currentUser.login(token);
            System.out.println("认证成功");
        }catch (Exception e){
            System.out.println(e);
            System.out.println("认证失败");
        }
        return currentUser;
    }
}
