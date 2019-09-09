package DAOs;

        import javax.persistence.EntityManager;

        import excepciones.*;
        import queMePongoClases.Evento;

public class EventoDAO {

    private EntityManager entityManager;

    public EventoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Evento obtenerEvento(long idEvento) {

        Evento evento = entityManager.find(Evento.class, idEvento);

        if(evento == null) {
            throw new UsuarioNoExisteException("No existe un usuario con el id: " + idEvento);
        }

        return evento;
    }

    public long guardarEvento(Evento evento) {

        long idEvento;

        if(evento.getId() != 0) {
            throw new EventoExistenteException("El Evento no es nuevo");
        }

        entityManager.persist(evento);
        entityManager.getTransaction().commit();
        idEvento = evento.getId();

        return idEvento;
    }

    public long eliminarEvento(Evento evento) {

        long idEvento;

        if(evento.getId() == 0) {
            throw new EventoExistenteException("El Evento no existe");
        }

        entityManager.remove(evento);
        //entityManager.getTransaction().commit();
        idEvento = evento.getId();

        return idEvento;
    }

    public void actualizarEvento(Evento evento) {

        if(evento.getId() == 0) {
            throw new EventoNoExistenteException("El evento es nuevo y no existe en la base de datos");
        }

        entityManager.getTransaction().commit();
    }
}
