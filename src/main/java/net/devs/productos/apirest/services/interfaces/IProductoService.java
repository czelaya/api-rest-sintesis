package net.devs.productos.apirest.services.interfaces;

import java.util.List;

import net.devs.productos.apirest.models.entities.Producto;

public interface IProductoService {

	public List<Producto> findAll();
	
	public Producto findById(Long  id);
	
	public Producto save(Producto producto);
	
	public List<Producto> isExist(String nombre);

}
