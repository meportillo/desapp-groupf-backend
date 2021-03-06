package persistence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import model.Event;
import model.Profile;
import model.User;

public class EventRepository extends HibernateGenericDAO<Event> implements GenericRepository<Event> {

	private static final long serialVersionUID = 4587472159808484719L;

	@Override
	protected Class<Event> getDomainClass() {

		return Event.class;
	}

	public List<Event> getEventByCantPerson(Integer cantPerson) {

		Session session = this.getSessionFactory().getCurrentSession();
		String hql = "FROM Event E WHERE E.cantPerson = :cantperson";
		Query query = session.createQuery(hql);
		query.setParameter("cantperson", cantPerson);
		return query.list();
	}

	public Event attendEvent(Integer idEvent, Integer idUser) {

		Session session = this.getSessionFactory().getCurrentSession();
		Event event = this.findById(idEvent);
		String hql = "FROM User U WHERE U.idUser = :idUser";
		Query query = session.createQuery(hql);
		query.setParameter("idUser", idUser);
		User user = (User) query.list().get(0);
		event.addUser(user);
		session.update(event);
		session.flush();
		return event;

	}

	public void associateUserToEvent(Integer idEvent, Integer idUser) {

		Session session = this.getSessionFactory().getCurrentSession();
		Event event = this.findById(idEvent);
		String hql = "FROM User U WHERE U.idUser = :idUser";
		Query query = session.createQuery(hql);
		query.setParameter("idUser", idUser);
		User user = (User) query.list().get(0);
		user.addToMyEvents(event);
		session.update(user);
		session.flush();

	}

	public List<Event> getEventByMusicalLike(List<String> likes) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = "FROM Event E WHERE E.genderMusical.nameGMusical in (:likes)";
		Query query = session.createQuery(hql);
		query.setParameterList("likes", likes);
		List<Event> events = (List<Event>) query.list();
		return events;
	}

	public List<Event> getEventMovieLike(List<String> likes) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = "FROM Event E WHERE E.genere.name in (:likes) ";
		Query query = session.createQuery(hql);
		query.setParameterList("likes", likes);
		List<Event> events = (List<Event>) query.list();
		return events;
	}

	public List<Event> getEventByFoodLike(List<String> likes) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = "FROM Event E WHERE E.typeFood.name in (:likes) ";
		Query query = session.createQuery(hql);
		query.setParameterList("likes", likes);
		List<Event> events = (List<Event>) query.list();
		return events;
	}

	public List<Event> getEventByOtherLikes(List<String> likes) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = "FROM Event E WHERE E.otherLike.name in (:likes) ";
		Query query = session.createQuery(hql);
		query.setParameterList("likes", likes);
		List<Event> events = (List<Event>) query.list();
		return events;
	}

}
