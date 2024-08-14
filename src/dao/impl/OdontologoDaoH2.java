package dao.impl;

import dao.IDao;
import db.H2Connection;
import model.Odontologo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class OdontologoDaoH2 implements IDao<Odontologo> {
    public static final Logger logger = Logger.getLogger(OdontologoDaoH2.class);
    public static final String INSERT = "INSERT INTO ODONTOLOGOS VALUES(DEFAULT,?,?,?);";
    public static final String SELECT_ALL = "SELECT * FROM ODONTOLOGOS";

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        Odontologo odontologoARetornar =  null;
        Connection conexion = null;

        try{
            conexion = H2Connection.getConnection();
            logger.info("Se ha establecido conexion con la DB H2 para guardar");
            conexion.setAutoCommit(false);
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            sentenciaPreparada.setInt(1, odontologo.getMatricula());
            sentenciaPreparada.setString(2, odontologo.getNombre());
            sentenciaPreparada.setString(3, odontologo.getApellido());
            sentenciaPreparada.execute();
            conexion.commit();
            logger.info("Los cambios han sido ejecutados con exito");

            //Devolver datos
            ResultSet tablaDatos = sentenciaPreparada.getGeneratedKeys();
            Integer id = null;
            if(tablaDatos.next()){
                id = tablaDatos.getInt(1);
            }

            odontologoARetornar = new Odontologo(id, odontologo.getMatricula(), odontologo.getNombre(), odontologo.getApellido());

            //Mostrar datos
            logger.info("Odontologo/a guardado correctamente: "+ odontologoARetornar);



        }catch (Exception e){
            try {
                conexion.rollback();
                logger.error("Hubo un problema, se hizo un rollback a la transaccion");
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
            }finally {
                try {
                    conexion.setAutoCommit(true);
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                }
            }
            logger.error(e.getMessage());
        }finally {
            try {
                conexion.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        return odontologoARetornar;
    }

    @Override
    public List<Odontologo> listarTodos() {
        Connection conexion = null;
        List<Odontologo> listaOdontologos = new ArrayList<>();
        Odontologo odontologo = null;


    try {
        conexion = H2Connection.getConnection();
        Statement sentencia = conexion.createStatement();
        ResultSet tabla_datos = sentencia.executeQuery(SELECT_ALL);
        while(tabla_datos.next()){
            Integer id = tabla_datos.getInt(1);
            Integer matricula = tabla_datos.getInt(2);
            String nombre = tabla_datos.getString(3);
            String apellido = tabla_datos.getString(4);
            odontologo = new Odontologo(id, matricula, nombre, apellido);

            logger.info(odontologo);
            listaOdontologos.add(odontologo);
        }

    } catch (Exception e) {
        e.printStackTrace();
        logger.error(e.getMessage());
    } finally {
        try {
            conexion.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

        return listaOdontologos;
    //la funcion termina aqui
    }




}
