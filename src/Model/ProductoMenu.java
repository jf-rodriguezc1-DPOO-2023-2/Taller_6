package Model;

public class ProductoMenu implements Producto{
	private String nombre;
	private int precioBase;
	private int calorias;
	
	
	public ProductoMenu(String nombre, int precioBase, int calorias)
	{
		this.nombre = nombre;
		this.precioBase = precioBase;
		this.calorias = calorias;
	}

	@Override
	public String getNombre()
	{
		return this.nombre;
	}
	
	@Override
	public int getPrecio()
	{
		return this.precioBase;
	}
	
	@Override
	public String generarTextoFactura()
	{
		String factura = "\n" + nombre + " ".repeat(10) + String.valueOf(precioBase);
		return factura;
	}
	
	@Override
	public int getCalorias()
	{
		return this.calorias;
	}
	
}

