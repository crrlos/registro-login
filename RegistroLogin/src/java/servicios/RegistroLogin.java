/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import clases.HibernateUtil;
import clases.Usuarios;
import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Carlos
 */
@WebService(serviceName = "RegistroLogin")
public class RegistroLogin {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "login")
    public int login(@WebParam(name = "usuario") String usuario, @WebParam(name = "clave") String clave) {
        //TODO write your implementation code here:
        ArrayList<Usuarios> lista = new ArrayList<>();

        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        lista = (ArrayList<Usuarios>) s.createQuery("from Usuarios").list();
        for (Usuarios u : lista) {
            if (u.getIdusuario().equals(usuario)) {
                if (u.getClave().equals(clave)) {
                    return 1;
                }
            }
        }
        t.commit();
        s.close();
        return 0;

    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "registro")
    public int registro(@WebParam(name = "usuario") String usuario, @WebParam(name = "email") String email, @WebParam(name = "clave") String clave) {
        ArrayList<Usuarios> lista = new ArrayList<>();
        int retorno = 0;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction t = s.beginTransaction();

            lista = (ArrayList<Usuarios>) s.createQuery("from Usuarios").list();

            for (Usuarios user : lista) {
                if (user.getIdusuario().equals(usuario)) {
                    return 2;
                }
                if (user.getEmail().equals(email)) {
                    return 3;
                }
            }

            Usuarios u = new Usuarios();
            u.setIdusuario(usuario);
            u.setClave(clave);
            u.setEmail(email);
            s.save(u);

            t.commit();
            s.close();

            return 1;
        } catch (Exception ex) {
        }

        //TODO write your implementation code here:
        return retorno;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "verificar")
    public boolean verificar(@WebParam(name = "usuario") String usuario) {
        ArrayList<Usuarios> lista = new ArrayList<>();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction t = s.beginTransaction();

            lista = (ArrayList<Usuarios>) s.createQuery("from Usuarios").list();
            t.commit();
            s.close();
            for (Usuarios user : lista) {
                if (user.getIdusuario().equals(usuario)) {
                    return true;
                }

            }

        } catch (Exception ex) {
        }
        return false;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "test")
    public String test(@WebParam(name = "usuario") String usuario) {
       ArrayList<Usuarios> lista = new ArrayList<>();
       Usuarios u = new Usuarios();
       u.setIdusuario("sdfsdfs");
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction t = s.beginTransaction();
                
           u =  (Usuarios) s.createQuery("from Usuarios where idusuario = '"+usuario+"'").uniqueResult();
            
            t.commit();
            s.close();
            

        } catch (Exception ex) {
            return ex.getMessage();
        }
        return u.getIdusuario();
    }

}
