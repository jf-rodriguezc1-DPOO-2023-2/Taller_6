package Model;

import java.util.ArrayList;

public class Combo implements Producto{
	private double descuento;
	
	private String nombreCombo;
	
	private ArrayList<ProductoMenu> itemsCombo;
	
	
	public Combo(String nombre, double descuento)
	{
		this.nombreCombo = nombre;
		this.descuento = descuento;
		this.itemsCombo = new ArrayList<ProductoMenu>();
	}
	
	public void agregarItemACombo(ProductoMenu itemCombo)
	{
		itemsCombo.add(itemCombo);
	}
	
	@Override
	public int getPrecio()
	{
		int precioCombo = 0;
		for (ProductoMenu producto : itemsCombo)
		{
			precioCombo += producto.getPrecio() * (1 - descuento);
		}
		
		return precioCombo;
	}
	
	@Override
	public int getCalorias()
	{
		int caloriasCombo = 0;
		for (ProductoMenu producto : itemsCombo)
		{
			caloriasCombo += producto.getCalorias();
		}
		
		return caloriasCombo;
	}
	public String generarTextoFactura()
	{
		String factura = "\n" + nombreCombo;
		for (Producto producto : itemsCombo)
		{
			factura += "\n" + producto.getNombre() + " ".repeat(10) + String.valueOf(producto.getPrecio()* (1 - descuento)) 
			+ " ".repeat(10) + String.valueOf(producto.getCalorias()) + " Calorias";
		}
		String precio = String.valueOf(this.getPrecio());
		factura += "\nTotal: " + " ".repeat(10) +  precio;
		
		return factura;
	}
	
	@Override
	public String getNombre()
	{
		return this.nombreCombo;
	}
	
	public double getDescuento()
	{
		return this.descuento;
	}
	
	public ArrayList<ProductoMenu> getItemsCombo()
	{
		return this.itemsCombo;
	}
	

}
