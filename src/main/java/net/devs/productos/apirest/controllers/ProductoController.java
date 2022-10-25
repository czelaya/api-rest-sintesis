package net.devs.productos.apirest.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.devs.productos.apirest.models.entities.Producto;
import net.devs.productos.apirest.services.interfaces.IProductoService;



@CrossOrigin(origins = "{*}")
@RestController
@RequestMapping("/api")
public class ProductoController {

	@Autowired
	private IProductoService productoService;
	
	@GetMapping("/productos")
	public List<Producto> getAll(){
		return productoService.findAll();
	}
	
	@GetMapping("/productos/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		Producto producto = null;
		Map<String, Object> response = new HashMap<>();
		try {
			producto = productoService.findById(id);
		}catch(DataAccessException e) {
			response.put("message", 
					"Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage());
		}
		if(producto == null) {
			response.put("message", 
					"El producto con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Producto>(producto,HttpStatus.OK);
	}
	
	@PostMapping("/productos")
	public ResponseEntity<?> save(@RequestBody Producto producto) throws IOException{
		String imageNewName = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			if(productoService.isExist(producto.getNombre()).size() > 0 && producto.getId() == null) {
				response.put("message", "Ya existe un registro con este nombre y descripción en la base de datos");
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CONFLICT);
			}else {
				producto.setImagen(imageNewName);
				productoService.save(producto);
			}
		}catch(DataAccessException e) {
			response.put("message", "Error al insertar registro en la base de datos");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "Producto registrado con exito...");
		response.put("producto", producto);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
}
