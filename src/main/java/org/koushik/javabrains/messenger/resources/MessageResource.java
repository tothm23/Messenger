package org.koushik.javabrains.messenger.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.koushik.javabrains.messenger.model.Message;
import org.koushik.javabrains.messenger.resources.beans.MessageFilterBean;
import org.koushik.javabrains.messenger.service.MessageService;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriInfo;

@Path("/messages")
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {

	MessageService messageService = new MessageService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getJsonMessages(
			/*
			 * @QueryParam("year") int year,
			 * 
			 * @QueryParam("start") int start,
			 * 
			 * @QueryParam("size") int size
			 */
			@BeanParam MessageFilterBean mesaFilterBean) {
		System.out.println("JSON method called");
		if (mesaFilterBean.getYear() > 0) {
			return messageService.getAllMessagesForYear(mesaFilterBean.getYear());
		}
		if (mesaFilterBean.getStart() > 0 && mesaFilterBean.getSize() > 0) {
			return messageService.getAllMessagesPaginated(mesaFilterBean.getStart(), mesaFilterBean.getSize());
		}
		return messageService.getAllMessages();
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Message> getXmlMessages(
			/*
			 * @QueryParam("year") int year,
			 * 
			 * @QueryParam("start") int start,
			 * 
			 * @QueryParam("size") int size
			 */
			@BeanParam MessageFilterBean mesaFilterBean) {
		System.out.println("XML method called");
		if (mesaFilterBean.getYear() > 0) {
			return messageService.getAllMessagesForYear(mesaFilterBean.getYear());
		}
		if (mesaFilterBean.getStart() > 0 && mesaFilterBean.getSize() > 0) {
			return messageService.getAllMessagesPaginated(mesaFilterBean.getStart(), mesaFilterBean.getSize());
		}
		return messageService.getAllMessages();
	}

	/*
	 * @POST public Message addMessage(Message message) { return
	 * messageService.addMessage(message); }
	 */

	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) throws URISyntaxException {
		Message newMessage = messageService.addMessage(message);
		String newId = String.valueOf(newMessage.getId());

		// return Response.status(Status.CREATED).entity(newMessage).build();

		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri).entity(newMessage).build();

	}

	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}

	@DELETE
	@Path("/{messageId}")
	public void removeMessage(@PathParam("messageId") long id) {
		messageService.removeMessage(id);
	}

	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
		Message message = messageService.removeMessage(id);

		message.addLink(getUriForSelf(uriInfo, message), "self");
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		message.addLink(getUriForComments(uriInfo, message), "comments");

		return message;
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder() // http://localhost:8080/messenger/webapi
				.path(MessageResource.class) // /messages
				.path(Long.toString(message.getId())) // /{messageId}
				.build().toString();
		return uri;
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder() // http://localhost:8080/messenger/webapi
				.path(ProfileResource.class) // /profiles
				.path(message.getAuthor()) // /{authorName}
				.build().toString();
		return uri;
	}

	private String getUriForComments(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder() // http://localhost:8080/messenger/webapi
				.path(MessageResource.class) // /messages
				.path(MessageResource.class, "getCommentResource").path(CommentResource.class)
				.resolveTemplate("messageId", message.getId()).build().toString();
		return uri;
	}

	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
}
