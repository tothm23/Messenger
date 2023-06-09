package org.koushik.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.koushik.javabrains.messenger.database.DatabaseClass;
import org.koushik.javabrains.messenger.model.Comment;
import org.koushik.javabrains.messenger.model.ErrorMessage;
import org.koushik.javabrains.messenger.model.Message;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class CommentService {

	private Map<Long, Message> messages = DatabaseClass.getMessages();

	public CommentService() {
		// messages.put(1L, new Message(1, "This is a comment!", "Milán"));
	}

	public List<Comment> getAllComments(long messageId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return new ArrayList<Comment>(comments.values());
	}

	public Comment getComment(long messageId, long commentId) {

		ErrorMessage errorMessage = new ErrorMessage("Not found", 404, "This is the documentation 2");
		Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
		
		Message message = messages.get(messageId);
		if (message == null) {
			throw new WebApplicationException(response);
		}

		Map<Long, Comment> comments = messages.get(messageId).getComments();
		
		Comment comment = comments.get(commentId);
		if (comment == null) {
			throw new NotFoundException(response);
		}

		return comment;
	}

	public Comment addComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();

		comment.setId(comments.size() + 1);
		comments.put(comment.getId(), comment);

		return comment;
	}

	public Comment updateComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();

		if (comment.getId() <= 0) {
			return null;
		}

		comments.put(comment.getId(), comment);
		return comment;
	}

	public Comment removeComment(long messageId, long commentId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return comments.remove(commentId);
	}
}
