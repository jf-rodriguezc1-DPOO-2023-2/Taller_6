package Model;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Restaurante {
	
	private ArrayList<Pedido> pedidos;
	private ArrayList<ProductoMenu> productos;
	private ArrayList<Ingrediente> ingredientes;
	private ArrayList<Combo> combos;
	private ArrayList<Bebidas> bebidas;
	private ArrayList<String> factura;
	private Pedido pedidoActual; 
	
 	public Restaurante()
	{
		this.pedidos = new ArrayList<Pedido>();
		this.productos = new ArrayList<ProductoMenu>();
		this.ingredientes = new ArrayList<Ingrediente>();
		this.combos = new ArrayList<Combo>();
		this.bebidas = new ArrayList<Bebidas>();
		this.factura = new ArrayList<String>();
	}
	
	public void iniciarPedido(String nombreCliente, String direccionCliente)
	{
		Pedido nuevoPedido = new Pedido(nombreCliente, direccionCliente);
		
		Random rnd = new Random();
		double id = rnd.nextDouble();
		String idString = String.valueOf(id); 

		nuevoPedido.setIdPedido(idString);
		
		pedidoActual = nuevoPedido;
	}
	
	public void cerrarYGuardarPedido()
	{
		int opcion = 0;
		pedidos.add(pedidoActual);
		factura.add(pedidoActual.getIdPedido());
		pedidoActual.GuardarFactura(opcion);
		pedidoActual = null;
	}
	
	public Pedido getPedidoEnCurso()
	{
		return pedidoActual;
	}
	
	public ArrayList<ProductoMenu> getMenuBase()
	{
		return productos;
	}
	
	public ArrayList<Ingrediente> getIngredientes()
	{
		return ingredientes;
	}
	
	public ArrayList<Pedido> getPedidos()
	{
		return pedidos;
	}
	
	public ArrayList<Combo> getCombos()
	{
		return combos;
	}
	
	public ArrayList<Bebidas> getBebidas()
	{
		return this.bebidas;
	}
	
	public ArrayList<String> getFacturas()
	{
		return factura;
	}
	
	public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos, File archivoBebidas)
	{
		cargarIngredientes(archivoIngredientes);
		cargarMenu(archivoMenu);
		cargarCombos(archivoCombos);
		cargarBebidas(archivoBebidas);
	}
	
	private void cargarIngredientes(File archivoIngredientes)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(archivoIngredientes));
			String linea = br.readLine();
			while(linea != null)
			{
				String[] partes = linea.split(";");
				String nombreIngrediente = partes[0];
				int precioIngrediente = Integer.parseInt(partes[1]);
				int caloriasIngrediente = Integer.parseInt(partes[2]);
				Ingrediente nuevo = new Ingrediente(nombreIngrediente, precioIngrediente, caloriasIngrediente);
				ingredientes.add(nuevo);
				linea = br.readLine();
			}
			br.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void cargarCombos(File archivoCombos)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(archivoCombos));
			String linea = br.readLine();
			while(linea != null)
			{
				String[] partes = linea.split(";");
				
				String nombreCombo = partes[0];
				String descuento = partes[1];
				
				double descuentoCombo = Double.parseDouble(descuento.replace("%", "")) / 100;
				Combo nuevo = new Combo(nombreCombo, descuentoCombo);
				
				
				for(ProductoMenu producto: productos)
				{
					if (producto.getNombre().equalsIgnoreCase(partes[2]))
					{
						nuevo.agregarItemACombo(producto);
					}
					if (producto.getNombre().equalsIgnoreCase(partes[3]))
					{
						nuevo.agregarItemACombo(producto);
					}
					if (producto.getNombre().equalsIgnoreCase(partes[4]))
					{
						nuevo.agregarItemACombo(producto);
					}
				}
				combos.add(nuevo);
				linea = br.readLine();
				
			}
			br.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void cargarBebidas(File archivoBebidas)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(archivoBebidas));
			String linea = br.readLine();
			while(linea != null)
			{
				String[] partes = linea.split(";");
				String nombreBebida = partes[0];
				int precioBebida = Integer.parseInt(partes[1]);
				int caloriasBebida = Integer.parseInt(partes[2]);
				Bebidas nuevo = new Bebidas(nombreBebida, precioBebida, caloriasBebida);
				bebidas.add(nuevo);
				linea = br.readLine();
			}
			br.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void cargarMenu(File archivoMenu)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(archivoMenu));
			String linea = br.readLine();
			while(linea != null)
			{
				String[] partes = linea.split(";");
				String nombreProducto = partes[0];
				int precioProducto = Integer.parseInt(partes[1]);
				int caloriasProducto = Integer.parseInt(partes[2]);
				
				
				ProductoMenu nuevo = new ProductoMenu(nombreProducto, precioProducto, caloriasProducto);
				productos.add(nuevo);
				linea = br.readLine();
			}
			br.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();	
		}
	}
	
	
	
	
}
