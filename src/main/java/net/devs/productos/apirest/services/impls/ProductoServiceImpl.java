package net.devs.productos.apirest.services.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.devs.productos.apirest.models.dao.IProductoDAO;
import net.devs.productos.apirest.models.entities.Producto;
import net.devs.productos.apirest.services.interfaces.IProductoService;

@Service
public class ProductoServiceImpl implements IProductoService{

	@Autowired
	private IProductoDAO productoDAO;
	
	@Override
	public List<Producto> findAll() {
		return (List<Producto>)productoDAO.findAll();
	}

	@Override
	public Producto findById(Long id) {
		return productoDAO.findById(id).orElse(null);
	}

	@Override
	public Producto save(Producto producto) {
		return productoDAO.save(producto);
	}

	@Override
	public List<Producto> isExist(String nombre) {
		return productoDAO.findByNombre(nombre);
	}

}
