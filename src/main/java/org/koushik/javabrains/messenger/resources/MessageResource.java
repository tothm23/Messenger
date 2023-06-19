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
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {

	MessageService messageService = new MessageService();

	@GET
	public List<Message> getMessages(
			/*
			 * @QueryParam("year") int year,
			 * 
			 * @QueryParam("start") int start,
			 * 
			 * @QueryParam("size") int size
			 */
			@BeanParam MessageFilterBean mesaFilterBean) {
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
		return message;
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder() // http://localhost:8080/messenger/webapi
				.path(MessageResource.class) // /messages
				.path(Long.toString(message.getId())) // /{messageId}
				.build().toString();
		return uri;
	}

	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
}
