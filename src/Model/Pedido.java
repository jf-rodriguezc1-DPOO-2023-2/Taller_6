package Model;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


public class Pedido {
	private int numeroPedidos;
	
	private String idPedido;
	
	private String nombreCliente;
	
	private String direccionCliente;
	
	private ArrayList<Producto> itemsProducto;
	
	public Pedido(String nombreCliente, String direccionCliente)
	{
		this.nombreCliente = nombreCliente;
		this.direccionCliente = direccionCliente;
		this.itemsProducto = new ArrayList<Producto>();
	}
	
	public int getNumeroPedidos()
	{
		return this.numeroPedidos;
	}
	
	public void setNumeroPedidos(int numeroPedidos)
	{
		this.numeroPedidos = numeroPedidos;
	}
	
	public String getIdPedido()
	{
		return this.idPedido;
	}
	
	
	public void setIdPedido(String idPedido)
	{
		this.idPedido = idPedido;
	}
	
	public ArrayList<Producto> getItemsProducto()
	{
		return this.itemsProducto;
	}
	
	public void setItemsProducto(ArrayList<Producto> itemsProducto)
	{
		this.itemsProducto = itemsProducto;
	}
	
	public void agregarProducto(Producto nuevoItem)
	{
		itemsProducto.add(nuevoItem);
	}

	private int getPrecioNetoPedido()
	{
		int total = 0;
		for (Producto producto : itemsProducto)
		{
			total += producto.getPrecio();
		}
		return total;
	}
	
	private double getPrecioTotalPedido()
	{
		int totalNeto = getPrecioNetoPedido();
		double precioIVA = getPrecioIVAPedido();
		double precioTotal = totalNeto + precioIVA;
		
		return precioTotal;
	}
	
	private double getPrecioIVAPedido()
	{
		int totalNeto = getPrecioNetoPedido();
		double totalIVA = totalNeto * 0.19;
		
		return totalIVA;
	}
	
	private int getCaloriasTotales()
	{
		int total = 0;
		for (Producto producto : itemsProducto)
		{
			total += producto.getCalorias();
		}
		return total;
	}
	
	private String generarInicioTextoFactura()
	{
		String factura = "Nombre: " + nombreCliente;
		factura += "\nDireccion: " + direccionCliente;
		factura += "\nFactura No. " + idPedido;
		factura += "\n" + "-".repeat(20);
		
		return factura;
	}
		
	private String generarFinalTextoFactura()
	{
		String factura = "";
		int totalNeto = getPrecioNetoPedido();
		double precioIVA = getPrecioIVAPedido();
		double precioTotal = getPrecioTotalPedido();
		factura += "\nTotal Neto: " + String.valueOf(totalNeto);
		factura += "\nIVA: " + String.valueOf(precioIVA);
		factura += "\nTOTAL: " + String.valueOf(precioTotal);
		int caloriasTotales = getCaloriasTotales();
		factura += "\nCalorias Totales: " + String.valueOf(caloriasTotales);
		
		return factura;
	}
	
	private String generarTextoFacturaElementos()
	{
		String factura = "";
		for (Producto producto : itemsProducto)
		{
			factura += producto.generarTextoFactura();
		}
		
		return factura;
	}
		
	private String generarTextoFactura()
	{
		String inicio = generarInicioTextoFactura();
		String cuerpo = generarTextoFacturaElementos();
		String end = generarFinalTextoFactura();
		
		String factura = inicio + cuerpo + end;
		
		return factura;
	}
	

	public void GuardarFactura(int opcion)
	{
		try
		{
			String factura = generarTextoFactura();
			File archivo = new File(System.getProperty("user.dir")+ "/data/" + "facturas/"+ idPedido +".txt");
			if (!archivo.exists())
				archivo.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
			bw.write(factura);
			bw.close();
			
			if (opcion == 1)
			{
				System.out.println(factura);
			}
		}
		catch (Exception err)
		{
		err.printStackTrace();
		System.out.println("Hubo un error guardando el archivo");
		}
	}
	
	public void mostarInfoPedido()
	{
		for (Producto producto : itemsProducto)
		{
			System.out.println(producto.getNombre() + " ".repeat(10) + String.valueOf(producto.getPrecio()));
		}
		int totalNeto = getPrecioNetoPedido();
		double precioIVA = getPrecioIVAPedido();
		double precioTotal = getPrecioTotalPedido();
		
		System.out.println("\nTotal Neto: " + totalNeto);
		System.out.println("\nIVA: " + precioIVA);
		System.out.println("\nTOTAL: " + precioTotal);
		
	}
	
	public boolean equals(Pedido otherPedido)
	{
		return this.getPrecioTotalPedido() == otherPedido.getPrecioTotalPedido() && getCaloriasTotales() == otherPedido.getCaloriasTotales();
	}
}



