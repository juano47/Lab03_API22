package padula.delaiglesia.dam.isi.frsf.lab03.dao;

import java.util.List;

import padula.delaiglesia.dam.isi.frsf.lab03.Categoria;
import padula.delaiglesia.dam.isi.frsf.lab03.Trabajo;

public interface ITrabajoDAO {
    public List<Categoria> listaCategoria();
    public void crearOferta(Trabajo p);
    public List<Trabajo> listaTrabajos();



}