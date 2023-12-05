package Model;

import java.util.ArrayList;


public class ProductoAjustado implements Producto{
	
	private ProductoMenu base;
	
	private ArrayList<Ingrediente> ingredientesAgregados;
	
	private ArrayList<Ingrediente> ingredientesEliminados;
	
	
	public ProductoAjustado(ProductoMenu base, ArrayList<Ingrediente> ingredientesAgregados, ArrayList<Ingrediente> ingredientesEliminados)
	{
		this.base = base;
		this.ingredientesAgregados = ingredientesAgregados;
		this.ingredientesEliminados = ingredientesEliminados;
	}
	
	@Override
	public String getNombre()
	{
		return base.getNombre();
	}
	
	@Override
	public int getPrecio()
	{
		
		int precioBase =  base.getPrecio();
		int precioAgregado = 0;
		for (Ingrediente ingrediente : ingredientesAgregados)
		{
			precioAgregado += ingrediente.getCostoAdicional();
		}
		int precioTotal = precioBase + precioAgregado;
		
		return precioTotal;
	}
	
	@Override
	public int getCalorias()
	{
		int caloriasTotales = base.getCalorias();;
		for (Ingrediente ingrediente : ingredientesAgregados)
		{
			caloriasTotales += ingrediente.getCalorias();
		}
		for (Ingrediente ingrediente : ingredientesEliminados)
		{
			caloriasTotales -= ingrediente.getCalorias();
		}
		
		return caloriasTotales;
	}
	
	public ArrayList<Ingrediente> getIngredientessAgregados()
	{
		return this.ingredientesAgregados;
	}
	
	public ArrayList<Ingrediente> getIngredientesEliminados()
	{
		return this.ingredientesEliminados;
	}
	
	public void addIngredienteAgregado(Ingrediente ingredienteNuevo)
	{
		ingredientesAgregados.add(ingredienteNuevo);
	}
	
	public void addIngredienteEliminado(Ingrediente ingredienteNuevo)
	{
		ingredientesEliminados.add(ingredienteNuevo);
	}
	
	public String generarTextoFactura()
	{
		String factura = base.generarTextoFactura();
		if (ingredientesAgregados.size() > 0)
		{
			factura += "\nAdición de: ";
			for ( Ingrediente ingrediente : ingredientesAgregados)
			{
				factura += "\n" + ingrediente.getNombre() + " ".repeat(10) + String.valueOf(ingrediente.getCostoAdicional())
						+ " ".repeat(10) + String.valueOf(ingrediente.getCalorias()) + " Calorias";
				
			}
		}
		if (ingredientesEliminados.size() > 0)
		{
			factura += "\nSin: ";
			for ( Ingrediente ingrediente : ingredientesEliminados)
			{
				factura += "\n" + ingrediente.getNombre() + " ".repeat(10) + String.valueOf(ingrediente.getCalorias()) + " Calorias";
			}
		}
		
		return factura;
	}
	
	

}
