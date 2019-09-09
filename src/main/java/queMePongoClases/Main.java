package queMePongoClases;

import Clima.ServicioMeteorologico;

//package queMePongoClases;
//
//
//import java.text.ParseException;
//import org.quartz.CronTrigger;
//import org.quartz.JobDetail;
//import org.quartz.Scheduler;
//import org.quartz.SchedulerException;
//import org.quartz.SchedulerFactory;
//import org.quartz.impl.StdSchedulerFactory;
//
//
public class Main {
	ServicioMeteorologico servicioMeteorologico;

	public void chequeoPeriodico() { // Seria el metodo al que llama el cron, solo esta teniendo en cuenta eventos
										// proximos
		RepositorioUsuarios repoUsuarios = RepositorioUsuarios.getInstance();

		repoUsuarios.getUsuariosActuales()
				.forEach(unUsuario -> unUsuario.sugerirAtuendosParaEventosProximos(servicioMeteorologico));
	}

}
//	
//	public static void main(String[] args) throws SchedulerException, ParseException {
//        RepositorioUsuarios repo = RepositorioUsuarios.getInstance();
//        SchedulerFactory schedulerFactory=new StdSchedulerFactory();
//        Scheduler scheduler= schedulerFactory.getScheduler();
//        JobDetail jobDetail=new JobDetail("myJobClass","myJob1",EventoPeriodico.class);
//        CronTrigger trigger=new CronTrigger("cronTrigger","myJob1","0 0/1 * * * ?"); // En este ultimo parametro va lo que toma del evento según qué tipo es.
//        scheduler.scheduleJob(jobDetail, trigger);
//        // Recorrer los usuarios del repo. Para cada usuario, chequear los eventos que sean periodicos y en base a esos objetos, crear triggers (el valor de la expresión de la frecuencia de ejecución se tomará de la clase).
//        // 
//        
//        
//        
//    
//        
//    }
//}
