package com.unl.practica2.base.Controller.dao.dao_models;

import java.util.Date;

import com.unl.practica2.base.Controller.dao.AdapterDao;
import com.unl.practica2.base.Controller.data_struct.list.LinkedList;
import com.unl.practica2.base.models.Album;

public class DaoAlbum extends AdapterDao<Album>{
    private Album obj;
    private LinkedList<Album> listaAlbum;
    public DaoAlbum() {
        super(Album.class);
        // TODO Auto-generated constructor stub
    }

    public Album getObj() {
        if (obj == null)
            this.obj = new Album();
        return this.obj;
    }

    public void setObj(Album obj) {
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

    public LinkedList<Album> getListAll() {
        if (listaAlbum == null) {
            this.listaAlbum = listAll();
        }
        return listaAlbum;
    }

    public static void main(String[] args) {
        DaoAlbum da = new DaoAlbum();
        da.getObj().setNombre("CAS");
        da.getObj().setId_banda(1);
        da.getObj().setFecha(new Date());
        if(da.save()) {
            System.out.println("Album guardado correctamente");
        } else {
            System.out.println("Error al guardar el album");
        }
    }
}
