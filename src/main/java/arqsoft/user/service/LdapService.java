package arqsoft.user.service;

import com.novell.ldap.*;
import java.io.UnsupportedEncodingException;
import javax.faces.context.FacesContext;

/**
 * Created by javergarav on 05/03/2017.
 */
public class LdapService {

    private LDAPConnection lc = new LDAPConnection();

    public Boolean login(String user, String password){

        if (connect()) {
            if (validatePassword(user, password)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public Boolean connect(){

        String ldapHost = "192.168.99.104";
        String dn = "cn=admin,dc=uwallet,dc=unal,dc=edu,dc=co";
        String password = "admin";
        int ldapPort =  LDAPConnection.DEFAULT_PORT;
        int ldapVersion = LDAPConnection.LDAP_V3;

        try {
            lc.connect(ldapHost, ldapPort);
            System.out.println("==== Connecting to LDAP Server ====");
            lc.bind(ldapVersion, dn, password.getBytes("UTF8"));
            System.out.println("==== Authenticated in server ====");
            return true;
        } catch (LDAPException | UnsupportedEncodingException ex) {
            System.out.println("==== ERROR to connecto to the LDAP Server ====");
            return false;
        }

    }


    public int creation(String user, String password,String name, String lastName){

        if (connect()) {
            if (create(user, password, name, lastName)) {
                return 200;
            } else {
                return 400;
            }
        } else {
            return 500;
        }

    }

    public Boolean validatePassword(String user, String password){

        String dn = "cn=" + user + ",ou=uwallet,dc=uwallet,dc=unal,dc=edu,dc=co";

        try {
            lc.bind(dn, password);
            System.out.println("==== Password validated ====");
            return true;
        } catch (LDAPException ex) {
            System.out.println("==== ERROR to validate the password ====");
            return false;
        }
    }


    public Boolean create(String user, String password,String name, String lastName){

        String dn = "cn=" + user + ",ou=uwallet,dc=uwallet,dc=unal,dc=edu,dc=co";

        FacesContext context = FacesContext.getCurrentInstance();
          try{
              LDAPEntry usuario = Datos(user, name, lastName, user, password, user);
              lc.add(usuario);
              lc.disconnect();
              System.out.println( "Usuario ingresado correctamente..." );
              return true;
          } catch(LDAPException ex){
              if (ex.getResultCode() == 68){
                  System.err.println("ERROR: El Usuario ya se encuentra ingresado");
              }
              return false;
          }

    }

    public LDAPEntry Datos(String idUser, String firstnameUser, String lastnameUser, String user, String passwordUser, String emailUser) {
       LDAPAttributeSet setAtr = new LDAPAttributeSet();
         setAtr.add(new LDAPAttribute("objectclass", new String("inetOrgPerson")));
         setAtr.add(new LDAPAttribute("objectclass", new String("posixAccount")));
         setAtr.add(new LDAPAttribute("objectclass", new String("top")));
         setAtr.add(new LDAPAttribute("cn", user));
         setAtr.add(new LDAPAttribute("givenname", firstnameUser));
         setAtr.add(new LDAPAttribute("sn", lastnameUser));
         setAtr.add(new LDAPAttribute("mail", emailUser));
         setAtr.add(new LDAPAttribute("userpassword", passwordUser));
       String dn;
           dn = "cn=" + user + ",ou=uwallet,dc=uwallet,dc=unal,dc=edu,dc=co";
       LDAPEntry newEntry = new LDAPEntry(dn, setAtr);
       return newEntry;
   }

   public Boolean deleteUsers(String user){
           String dn = null;

           dn = "cn=" + user + ",ou=Shop,dc=arqsoft,dc=unal,dc=edu,dc=co";
           System.out.println( user );
           if (connect()){
               System.out.println( user );
               try {
                   System.out.println("Intentando borrar.");
                   lc.delete(dn);
                   System.out.println("El usuario fue eliminado correctamente.");
                   lc.disconnect();
                   System.out.println( "main-admin" );
                   return true;
               } catch (LDAPException e){
                   if (e.getResultCode() == LDAPException.NO_SUCH_OBJECT){
                       System.out.println("No existia.");
                       return false;
                   } else if (e.getResultCode() == LDAPException.INSUFFICIENT_ACCESS_RIGHTS) {
                       System.out.println("Sin permisos.");
                       return false;
                   } else {
                       System.out.println("Error: " + e.toString());
                       System.out.println( "No se pudo realizar la acción." );
                       return false;
                   }
               }
           } else {
               System.out.println("Conexión al servidor LDAP fallida.");
               System.out.println( "Conexión al servidor LDAP fallida." );
               return false;
           }
       }



}
