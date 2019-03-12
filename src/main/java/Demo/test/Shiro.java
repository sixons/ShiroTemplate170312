package Demo.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import util.ShiroUtil;

import java.util.Arrays;

/**
 *
 */
public class Shiro {
    @Test
    public void testWithoutUtilsSuccess() {
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("jack", "123");
        try {
            currentUser.login(token);
            System.out.println("认证成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("认证失败");
        }
        currentUser.logout();
    }
    @Test
    public void testWithUtilsFail() {
        Subject wrongUser = ShiroUtil.login("shiro.ini", "wrongUser", "123");
    }


    @Test
    public void testHasRole(){
        Subject currentUser = ShiroUtil.login("shiroRole.ini", "jack", "123");
        System.out.println(currentUser.hasRole("role1")?"有角色role1":"没有角色role1");
    }
    @Test
    public void testHasRoles(){
        Subject currentUser = ShiroUtil.login("shiroRole.ini", "jack", "123");
        System.out.println(currentUser.hasRole("role1") ? "有角色role1" : "没有角色role1");
        boolean[] bs = currentUser.hasRoles(Arrays.asList("role1", "role2", "role3"));
        System.out.println(bs[0]?"has role1":"no role1");
        System.out.println(bs[1]?"has role2":"no role2");
        System.out.println(bs[2]?"has role3":"no role3");
    }
    @Test
    public void testHasAllRoles(){
        Subject currentUser = ShiroUtil.login("shiroRole.ini", "jack", "123");
        System.out.println(currentUser.hasAllRoles(Arrays.asList("role1", "role2", "role3"))?"has all":"not all");
    }
    @Test
    public void testCheckRole(){
        Subject currentUser = ShiroUtil.login("shiroRole.ini", "jack", "123");
        try {
            currentUser.checkRole("role3");
            System.out.println("has role3");
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("no role3");
        }
    }
    @Test
    public void testCheckRolesWithStringList(){
        Subject currentUser = ShiroUtil.login("shiroRole.ini", "jack", "123");
        try {
            currentUser.checkRoles(Arrays.asList("role1", "role2"));
            System.out.println("has role1 and role2");
        }catch(Exception e){
            System.out.println("not role1 or role2");
        }
    }
    @Test
    public void testCheckRolesWithStringArray(){
        Subject currentUser = ShiroUtil.login("classpath:shiroRole.ini", "jack", "123");
        try {
            currentUser.checkRoles("role1", "role2");
            System.out.println("has role1 and role2");
        }catch(Exception e){
            System.out.println("not role1 or role2");
        }
    }

    @Test
    public void testIsPermitted(){
        Subject currentUser = ShiroUtil.login("shiroPermission.ini", "jack", "123");
        System.out.println(currentUser.isPermitted("user:select")?"has select":"not select");
        System.out.println(currentUser.isPermitted("user:delete")?"has delete":"not delete");
        System.out.println("------------------------------------------------------------------------");
        boolean[] bs = currentUser.isPermitted("user:select", "user:delete");
        System.out.println(bs[0]?"has select":"not select");
        System.out.println(bs[1]?"has delete":"not delete");
    }
    @Test
    public void testIsPermittedAll(){
        Subject currentUser = ShiroUtil.login("shiroPermission.ini", "jack", "123");
        System.out.println(currentUser.isPermittedAll("user:select", "user:delete")?"has select and delete":"not select or delete");
    }
    @Test
    public void testIsCheckPermission(){
        Subject currentUser = ShiroUtil.login("shiroPermission.ini", "jack", "123");
        try {
            currentUser.checkPermission("user:select");
            System.out.println("has select");
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("no select");
        }
    }

    @Test
    public void testJdbcRealm() {
        Subject currentUser = ShiroUtil.login("JdbcRealm.ini", "jack", "123");
    }
}
