package test;

import dao.impl.OdontologoDaoMemory;
import model.Odontologo;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.OdontologoService;


import static org.junit.jupiter.api.Assertions.*;

class OdontologoDaoMemoryTest {
    public static final Logger logger = Logger.getLogger(OdontologoDaoMemoryTest.class);
    OdontologoService odontologoService = new OdontologoService(new OdontologoDaoMemory());

    @Test
    @DisplayName("Testear que un odontologo se guarde en la DB")
    void caso1(){
        Odontologo odontologo1 = new Odontologo(202003, "PEPITO", "GONZALES");
        Odontologo odontologoDesdeDB = odontologoService.guardarOdontologo(odontologo1);

        assertNotNull(odontologoDesdeDB.getId());
    }


}