package com.unl.practica2.base.Controller.dao.dao_models;

import com.unl.practica2.base.Controller.dao.AdapterDao;
import com.unl.practica2.base.Controller.data_struct.list.LinkedList;
import com.unl.practica2.base.models.Genero;

public class DaoGenero extends AdapterDao<Genero> {
    private Genero obj;
    private LinkedList<Genero> listaGenero;
    public DaoGenero() {
        super(Genero.class);
        // TODO Auto-generated constructor stub
    }

    public Genero getObj() {
        if (obj == null)
            this.obj = new Genero();
        return this.obj;
    }

    public void setObj(Genero obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            obj.setId(listAll().getLength()+1);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            //TODO
            return false;
            // TODO: handle exception
        }
    }

    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;
        } catch (Exception e) {
            //TODO
            return false;
            // TODO: handle exception
        }
    }

    public LinkedList<Genero> getListAll() {
        if (listaGenero == null) {
            this.listaGenero = listAll();
        }
        return listaGenero;
    }


    public static void main(String[] args) {
        DaoGenero da = new DaoGenero();
        da.getObj().setNombre("Rock");
        if(da.save()) {
            System.out.println("Genero guardado correctamente");
        } else {
            System.out.println("Error al guardar el genero");
        }
}
}
