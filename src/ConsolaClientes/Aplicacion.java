package ConsolaClientes;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import Model.Ingrediente;
import Model.Producto;
import Model.Combo;
import Model.Bebidas;
import Model.Pedido;
import Model.ProductoMenu;
import Model.ProductoAjustado;
import java.util.ArrayList;
import Model.Restaurante;
import java.io.File;
import java.util.HashMap;

public class Aplicacion {
	public static void main(String[] args)
	{
		Aplicacion consola = new Aplicacion();
		consola.ejecutarAplicacion();
	}
	
	public void mostrarMenu()
	{
		System.out.println("\nOpciones de la aplicación\n");
		System.out.println("1. Menu");
		System.out.println("2. Iniciar pedido nuevo");
		System.out.println("3. Agregar elemento al pedido");
		System.out.println("4. Cerrar pedido y guardar factura");
		System.out.println("5. Consultar informacion de pedido por ID");
		System.out.println("6. Salir de la aplicación");
		
	}
			
	public String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje + ":\n");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}
	
	public void cargarInformacionRestaurante(Restaurante restaurante)
	{
		File ingredientes = new File("/Users/juan/Desktop/Taller2/data/ingredientes.txt");
		File menu = new File("/Users/juan/Desktop/Taller2/data/menu.txt");
		File combos = new File("/Users/juan/Desktop/Taller2/data/combos.txt");
		File bebidas = new File("/Users/juan/Desktop/Taller2/data/bebidas.txt");
		
		restaurante.cargarInformacionRestaurante(ingredientes, menu, combos, bebidas);
		
	}
	
	private HashMap<Integer, ProductoMenu> mostrarMenuProductos(Restaurante restaurante)
	{
		ArrayList<ProductoMenu> listaProductos = restaurante.getMenuBase();
		HashMap<Integer, ProductoMenu> numeroProducto = new HashMap<Integer, ProductoMenu>();
		
		int num = 1;
		for (ProductoMenu producto : listaProductos)
		{
			System.out.println("\n" + String.valueOf(num) + ") " + "Producto: " + producto.getNombre());
			System.out.println("   Precio:\n" + producto.getPrecio());
			numeroProducto.put(num, producto);
			num += 1;
		}
		return numeroProducto;
	}
	
	private HashMap<Integer, Combo> mostrarMenuCombos(Restaurante restaurante)
	{
		ArrayList<Combo> listaCombos = restaurante.getCombos();
		HashMap<Integer, Combo> numeroCombo = new HashMap<Integer, Combo>();
		
		int num = 1;
		for (Combo combo : listaCombos)
		{
			System.out.println("\n" + String.valueOf(num) + ") " + "Producto: " + combo.getNombre());
			System.out.println("   Precio:\n" + combo.getPrecio());
			numeroCombo.put(num, combo);
			num += 1;
		}
		return numeroCombo;
	}
	
	private HashMap<Integer, Bebidas> mostrarMenuBebidas(Restaurante restaurante)
	{
		ArrayList<Bebidas> listaBebidas = restaurante.getBebidas();
		HashMap<Integer, Bebidas> numeroBebida = new HashMap<Integer, Bebidas>();
		
		int num = 1;
		for (Bebidas bebida : listaBebidas)
		{
			System.out.println("\n" + String.valueOf(num) + ") " + "Producto: " + bebida.getNombre());
			System.out.println("   Precio:\n" + bebida.getPrecio());
			numeroBebida.put(num, bebida);
			num += 1;
		}
		return numeroBebida;
	}
	
	private HashMap<Integer, Ingrediente> mostrarMenuIngredientes(Restaurante restaurante)
	{
		ArrayList<Ingrediente> listaIngredientes = restaurante.getIngredientes();
		HashMap<Integer, Ingrediente> numeroIngrediente = new HashMap<Integer, Ingrediente>();
		int num = 1;
		for (Ingrediente producto : listaIngredientes)
		{
			System.out.println("\n" + String.valueOf(num) + ") " + "Producto: " + producto.getNombre());
			System.out.println("   Precio:\n" + producto.getCostoAdicional());
			numeroIngrediente.put(num, producto);
			num += 1;
		}
		return numeroIngrediente;
	}
	
	private void ejecutarInicarNuevoPedido(Restaurante restaurante)
	{
		String nombre = input("Nombre: ");
		String direccion = input("Dirección: ");
		restaurante.iniciarPedido(nombre, direccion);		
	}
	
	private void ejecutarAgregarProducto(Restaurante restaurante)
	{
		System.out.println("¿De qué menú desea agregar a su pedido?");
		System.out.println("1) Menú de productos");
		System.out.println("2) Menú de combos");
		System.out.println("2) Menú de bebidas");
		int opcion_menu = Integer.parseInt(input("Por favor seleccione una opción"));
		
		if (opcion_menu == 1)
		{
			HashMap<Integer, ProductoMenu> mapaProducto = mostrarMenuProductos(restaurante);
			int opcion = Integer.parseInt(input("Por favor seleccione una opción"));
			for(Integer numero :  mapaProducto.keySet() )
			{	
				boolean centinela = true;

				if (numero.equals(Integer.valueOf((opcion))))
				{
					Pedido pedidoActual = restaurante.getPedidoEnCurso();
					pedidoActual.agregarProducto(mapaProducto.get(numero));
					
					ArrayList<Ingrediente> ingredientesAgregados = new ArrayList<Ingrediente>();
					ArrayList<Ingrediente> ingredientesEliminados = new ArrayList<Ingrediente>();
					ProductoMenu productoBase = mapaProducto.get(numero);
					ProductoAjustado nuevoProductoAjustado = new ProductoAjustado(productoBase, ingredientesAgregados, ingredientesEliminados);
					
					
					while (centinela)
					{
						System.out.println("¿Desea hacerle alguna modificación a su producto?");
						System.out.println("1) Si");
						System.out.println("2) No");
						int opcionCambio = Integer.parseInt(input("Por favor seleccione una opción"));
						
						if(opcionCambio == 1)
						{	
							System.out.println("1) Deseo añadirle algo a mi pedido");
							System.out.println("2) Deseo eliminar algo de mi pedido");
							int opcion2 = Integer.parseInt(input("Por favor seleccione una opción"));
							if(opcion2 == 1)
							{
								AgregarOEliminar(restaurante, productoBase, nuevoProductoAjustado, "añadir");
								pedidoActual.agregarProducto(nuevoProductoAjustado);
							}
							else if(opcion2 == 2)
							{
								AgregarOEliminar(restaurante, productoBase, nuevoProductoAjustado, "eliminar");
							}
							else
							{
								System.out.println("Seleccione una opción válida");
							}
						}
						else if(opcionCambio == 2)
						{
							
							centinela = false;
						}
						else
						{
							System.out.println("Seleccione una opción válida");
						}
					}
				}
				if (mapaProducto.containsKey(numero) == false)
				{
					System.out.println("Seleccione una opción válida");
				}
				
			}
		}	
		if (opcion_menu == 2)
		{
			HashMap<Integer, Combo> mapaCombo = mostrarMenuCombos(restaurante);
			int opcion_2 = Integer.parseInt(input("Por favor seleccione una opción"));
			
			for(Integer numero_2 :  mapaCombo.keySet() )
			{
				
				if (mapaCombo.containsKey(numero_2) == false)
				{
					System.out.println("Seleccione una opción válida");
				}
				if (numero_2 == Integer.valueOf(opcion_2))
				{
					Pedido pedidoActual = restaurante.getPedidoEnCurso();
					pedidoActual.agregarProducto(mapaCombo.get(numero_2));
				}
				
			}
		}
		if (opcion_menu == 3)
		{
			HashMap<Integer, Bebidas> mapaBebidas = mostrarMenuBebidas(restaurante);
			int opcion_3 = Integer.parseInt(input("Por favor seleccione una opción"));
			
			for(Integer numero_3 :  mapaBebidas.keySet() )
			{
				
				if (mapaBebidas.containsKey(numero_3) == false)
				{
					System.out.println("Seleccione una opción válida");
				}
				if (numero_3 == Integer.valueOf(opcion_3))
				{
					Pedido pedidoActual = restaurante.getPedidoEnCurso();
					pedidoActual.agregarProducto(mapaBebidas.get(numero_3));
				}
				
			}
		}
		else
		{
			System.out.println("Seleccione una opción válida");
		}

	}
	
	private void AgregarOEliminar(Restaurante restaurante, Producto productoBase, ProductoAjustado productoAjustado, String opcion)
	{
		HashMap<Integer, Ingrediente> mapaIngrediente = mostrarMenuIngredientes(restaurante);
		int opcionMenu = Integer.parseInt(input("Seleccione el ingrediente que desea " + opcion));
		
		for(Integer numero :  mapaIngrediente.keySet() )
		{
			if (numero == Integer.valueOf(opcionMenu))
			{
				if (opcion == "añadir")
				{
					productoAjustado.addIngredienteAgregado(mapaIngrediente.get(numero));
				}
				if (opcion == "eliminar")
				{
					productoAjustado.addIngredienteEliminado(mapaIngrediente.get(numero));
				}
			}
			else if (mapaIngrediente.containsKey(numero) == false)
			{
				System.out.println("Seleccione una opción válida");
			}
			
		}
	}
	
	private void ejecutarCerrarYGuardarPedido(Restaurante restaurante)
	{
		System.out.println("¿Desea mirar su factura?");
		System.out.println("1)Si");
		System.out.println("2)No");
		int opcion = Integer.parseInt(input("Seleccione una opción: "));
		
		Pedido pedidoActual = restaurante.getPedidoEnCurso();
		pedidoActual.GuardarFactura(opcion);
		
		}
	
	private void ejecutarConsultarPorID(Restaurante restaurante)
	{
		ArrayList<String> facturas = restaurante.getFacturas();
		System.out.println("¿Cuál es el ID que desea consultar?");
		String id = input("Digítelo a continuación: ");
		ArrayList<Pedido> pedidos = restaurante.getPedidos();
		
		if (facturas.contains(id))
		{
			for (Pedido pedido : pedidos)
			{
				if (pedido.getIdPedido() == id)
				{
					pedido.mostarInfoPedido();
				}
			}
		}
		else
		{
			System.out.println("Este ID no existe");
		}
		
		
		
	}
	
	public void ejecutarAplicacion()
	{
		Restaurante restaurante = new Restaurante();
		cargarInformacionRestaurante(restaurante);
		boolean continuar = true;
		while (continuar)
		{
			try
			{
				mostrarMenu();
				int opcionSeleccionada = Integer.parseInt(input("Por favor seleccione una opción"));
				if (opcionSeleccionada == 1)
					mostrarMenuProductos(restaurante);
				else if (opcionSeleccionada == 2 )
					ejecutarInicarNuevoPedido(restaurante);
				else if (opcionSeleccionada == 3 )
					ejecutarAgregarProducto(restaurante);
				else if (opcionSeleccionada == 4 )
					ejecutarCerrarYGuardarPedido(restaurante);
				else if (opcionSeleccionada == 5 )
					ejecutarConsultarPorID(restaurante);
				else if (opcionSeleccionada == 6 )
				{
					System.out.println("Saliendo de la aplicación ...");
					continuar = false;
				}
				else
				{
					System.out.println("Por favor seleccione una opción válida.");
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println("Debe seleccionar uno de los números de las opciones.");
			}
		}		
	}
		

}

