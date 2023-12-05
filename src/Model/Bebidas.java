package Model;

public class Bebidas implements Producto{
	private String nombre;
	private int precioBebida;
	private int calorias;
	
	
	public Bebidas(String nombre, int precioBebida, int calorias)
	{
		this.nombre = nombre;
		this.precioBebida = precioBebida;
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
		return this.precioBebida;
	}
	
	@Override
	public String generarTextoFactura()
	{
		String factura = "\n" + nombre + " ".repeat(10) + String.valueOf(precioBebida);
		return factura;
	}
	
	@Override
	public int getCalorias()
	{
		return this.calorias;
	}
}
