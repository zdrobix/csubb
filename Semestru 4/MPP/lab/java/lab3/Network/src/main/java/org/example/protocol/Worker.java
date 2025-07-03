package org.example.protocol;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.domain.Child;
import org.example.domain.Event;
import org.example.domain.LoginInfo;
import org.example.domain.Signup;
import org.example.services.IObserver;
import org.example.services.IService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Worker implements Runnable, IObserver {
    private final IService server;
    private final Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    private static Logger logger = LogManager.getLogger(Worker.class);

    public Worker(IService server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }
    }

    public void run() {
        logger.debug("Worker thread started for client: {}", connection.getRemoteSocketAddress());
        while(connected){
            try {
                Object request=input.readObject();
                logger.debug("Received request from client: {}", request);
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException|ClassNotFoundException e) {
                logger.error(e.getMessage());
                connected = false;
                e.printStackTrace();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                logger.error(e);
                logger.error(e.getStackTrace());
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            logger.error("Error {}",e);
        }
    }

    private static Response okResponse() { return new Response().setType(ResponseType.OK);}

    private Response handleRequest(Request request) {
        Response response = null;
        switch (request.getType()){
            case LOGIN: {
                var login = (LoginInfo)request.getEntity();
                var actual = server.GetByUsername(login.GetUsername());
                if (login.GetPassword().equals(actual.GetPassword())) {
                    server.login(actual, this);
                    response = okResponse();
                }
                else return new Response().setType(ResponseType.ERROR).setMessage("Invalid username or password");
                break;
            }
            case ADD_CHILD: {
                Child child = (Child) request.getEntity();
                response = new Response().setType(ResponseType.CHILD_ADDED).setEntity(server.AddChild(child.GetName(), child.GetCNP()));
                break;
            }
            case ADD_EVENT: {
                Event event = (Event) request.getEntity();
                response = new Response().setType(ResponseType.EVENT_ADDED).setEntity(server.AddEvent(event.GetName(), event.GetMinAge(), event.GetMaxAge()));
                break;
            }
            case ADD_SIGNUP: {
                Signup signup = (Signup) request.getEntity();
                response = new Response().setType(ResponseType.SIGNUP_ADDED).setEntity(server.AddSignup(signup.GetChild(), signup.GetEvent()));
                break;
            }
            case GET_CHILDREN: {
                response = new Response().setType(ResponseType.CHILDREN_RECEIVED).setEntities(StreamSupport.stream(server.GetAllChildren().spliterator(), false)
                                    .collect(Collectors.toList()));
                break;
            }
            case GET_EVENTS: {
                response = new Response().setType(ResponseType.EVENTS_RECEIVED).setEntities(StreamSupport.stream(server.GetAllEvents().spliterator(), false)
                        .collect(Collectors.toList()));
                break;
            }
            case GET_SIGNUPS: {
                response = new Response().setType(ResponseType.SIGNUPS_RECEIVED).setEntities(StreamSupport.stream(server.GetAllSignups().spliterator(), false)
                        .collect(Collectors.toList()));
                break;
            }
        }
        return response;
    }


    private void sendResponse(Response response) throws IOException{
        logger.debug("sending response {}", response);
        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }

    @Override
    public void childAdded(Child child) {
        var response = new Response().setType(ResponseType.CHILD_ADDED).setEntity(child);
        logger.debug("Child added {}", child.GetName());
        try {
            sendResponse(response);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @Override
    public void eventAdded(Event event) {
        var response = new Response().setType(ResponseType.EVENT_ADDED).setEntity(event);
        logger.debug("Event added {}", event.GetName());
        try {
            sendResponse(response);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @Override
    public void signupAdded(Signup signup) {
        var response = new Response().setType(ResponseType.SIGNUP_ADDED).setEntity(signup);
        logger.debug("Signup added {}", signup.GetChild().GetName() + " " + signup.GetEvent().GetName());
        try {
            sendResponse(response);
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
