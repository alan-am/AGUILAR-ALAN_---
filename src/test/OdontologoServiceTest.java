package test;

import dao.impl.OdontologoDaoH2;
import model.Odontologo;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.OdontologoService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OdontologoServiceTest {
    //inicializar
    public static final Logger logger = Logger.getLogger(OdontologoServiceTest.class);
    static final OdontologoService odontologoService= new OdontologoService(new OdontologoDaoH2());
    @BeforeAll
    static void cargarTablas(){
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:./DB_Clinica;INIT=RUNSCRIPT FROM 'Create.sql'", "sa", "sa");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Testear que un odontologo se guarde en la DB")
    void caso1(){
        Odontologo odontologo1 = new Odontologo(202003, "PEPITO", "GONZALES");
        Odontologo odontologoDesdeDB = odontologoService.guardarOdontologo(odontologo1);

        assertNotNull(odontologoDesdeDB.getId());
    }


    @Test
    @DisplayName("Testear la salida de la lista de odontologos")
    void caso2(){
        List<Odontologo> odontologos = new ArrayList<>();

        odontologos = odontologoService.listar();

        assertFalse(odontologos.isEmpty());
    }




}