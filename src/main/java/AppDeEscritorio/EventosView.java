package AppDeEscritorio;

import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.NumericField;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.MainWindow;

import queMePongoClases.Evento;

@SuppressWarnings("serial")
public class EventosView extends MainWindow<EventosViewModel> {

	public EventosView() {
		super(new EventosViewModel());
	}

	@Override
	public void createContents(Panel mainPanel) {
		this.setTitle("Listado de eventos");
		mainPanel.setLayout(new VerticalLayout());
		
		new Label(mainPanel).setText("Ingrese una fecha inicio:");
		new NumericField(mainPanel)
			.setWidth(10)
			.bindValueToProperty("diaInicio");
		
		new NumericField(mainPanel)
			.setWidth(10)
			.bindValueToProperty("mesInicio");
		new NumericField(mainPanel)
			.setWidth(10)
			.bindValueToProperty("anioInicio");
		
		new Label(mainPanel).setText("Ingrese una fecha final:");
		new NumericField(mainPanel)
			.setWidth(10)
			.bindValueToProperty("diaFinal");
		new NumericField(mainPanel)
			.setWidth(10)
			.bindValueToProperty("mesFinal");
		new NumericField(mainPanel)
			.setWidth(10)
			.bindValueToProperty("anioFinal");
		
		new Button(mainPanel)
		.setCaption("Mostrar eventos")
		.onClick(()-> this.getModelObject().cargarEventos());
		
		Table<Evento> tablaEventos = new Table<Evento>(mainPanel, Evento.class);
		tablaEventos.bindItemsToProperty("eventos");
		tablaEventos.setNumberVisibleRows(10);
		
		new Column<Evento>(tablaEventos) 
	    .setTitle("Fecha")
	    .setFixedSize(300)
	    .bindContentsToProperty("fechaEvento");
		
		new Column<Evento>(tablaEventos) 
		.setTitle("Título")
		.setFixedSize(200)
		.bindContentsToProperty("descripcionEvento");
		
		new Column<Evento>(tablaEventos)
	    .setTitle("Posee Sugerencia")
	    .setFixedSize(150)
	    .bindContentsToProperty("poseeSugerencias");

	}

	public static void main(String[] args) {
		new EventosView().startApplication();
	}
}
