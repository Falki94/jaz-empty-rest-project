package rest;

import domain.Car;
import domain.Person;
import javax.ws.rs.Path;
import domain.services.PersonService;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/people")
public class PeopleResources {

    private PersonService db = new PersonService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAll() {
        return db.getAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response Add(Person person) {
        db.add(person);
        return Response.ok(person.getId()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        Person result = db.get(id);
        if (result == null) {
            return Response.status(404).build();
        }
        return Response.ok(result).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Person p) {
        Person result = db.get(id);
        if (result == null) {
            return Response.status(404).build();
        }
        p.setId(id);
        db.update(p);
        return Response.ok().build();
    }

    @GET
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") int id) {
        Person result = db.get(id);
        if (result == null) {
            return Response.status(404).build();
        }
        db.delete(result);
        return Response.ok().build();
    }

    @GET
    @Path("/{personId}/cars")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Car> getCars(@PathParam("personId") int personId) {
        Person result = db.get(personId);
        if (result == null) {
            return null;
        }
        if (result.getCars() == null) {
            result.setCars(new ArrayList<Car>());
        }
        return result.getCars();
    }

    @POST
    @Path("/{id}/cars")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCar(@PathParam("id") int personId, Car car) {
        Person result = db.get(personId);
        if (result == null) {
            return Response.status(404).build();
        }
        if (result.getCars() == null) {
            result.setCars(new ArrayList<Car>());
        }
        result.getCars().add(car);
        return Response.ok().build();
    }
}
