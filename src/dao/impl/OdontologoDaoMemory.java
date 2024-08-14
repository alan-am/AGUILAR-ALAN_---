package dao.impl;

import dao.IDao;
import model.Odontologo;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class OdontologoDaoMemory implements IDao<Odontologo> {
    public static final Logger logger = Logger.getLogger(OdontologoDaoMemory.class);
    private List<Odontologo> odontologos = new ArrayList<>();


    @Override
    public Odontologo guardar(Odontologo odontologo) {
        odontologo.setId(odontologos.size()+1);
        odontologos.add(odontologo);

        logger.info(odontologo);
        return odontologo;


    }

    @Override
    public List<Odontologo> listarTodos() {
        return null;
    }



}
