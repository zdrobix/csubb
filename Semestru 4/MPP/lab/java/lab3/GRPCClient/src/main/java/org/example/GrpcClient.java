package org.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.example.domain.Child;
import org.example.domain.Event;
import org.example.services.*;
import networking.grpc.*;

import java.util.ArrayList;
import java.util.List;

public class GrpcClient implements IService {
    private final String host;
    private final int port;
    private IObserver client;
    private ManagedChannel channel;
    private final ChildrenEventsServiceGrpc.ChildrenEventsServiceBlockingStub blockingStub;
    private final ChildrenEventsServiceGrpc.ChildrenEventsServiceStub asyncStub;

    public GrpcClient(String host, int port) {
        this.host = host;
        this.port = port;

        this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        this.blockingStub = ChildrenEventsServiceGrpc.newBlockingStub(channel);
        this.asyncStub = ChildrenEventsServiceGrpc.newStub(channel);
    }

    @Override
    public boolean login(org.example.domain.LoginInfo login, IObserver client) {
        this.client = client;
        try {
            Request request = networking.grpc.Request.newBuilder()
                    .setType(RequestType.LOGIN)
                    .setEntity(
                            networking.grpc.Entity.newBuilder()
                                    .setLoginInfo(
                                            networking.grpc.LoginInfo.newBuilder()
                                                    .setUsername(login.GetUsername())
                                                    .setPassword(login.GetPassword())
                                                    .setId(login.GetId())
                                                    .build()
                                    )
                                    .build()

                    )
                    .build();
            Response response = this.blockingStub.login(request);
            System.out.println(response);
            if (response.getType() == ResponseType.OK) {
                this.Subscribe((int)login.GetId());
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean logout(org.example.domain.LoginInfo login, IObserver client) {
        try {
            Request request = networking.grpc.Request.newBuilder()
                    .setType(RequestType.LOGOUT)
                    .setEntity(
                            networking.grpc.Entity.newBuilder()
                                    .setLoginInfo(
                                            networking.grpc.LoginInfo.newBuilder()
                                                    .setUsername(login.GetUsername())
                                                    .setPassword(login.GetPassword())
                                                    .setId(login.GetId())
                                                    .build()
                                    )
                                    .build()

                    )
                    .build();
            Response response = this.blockingStub.logout(request);
            if (response.getType() == ResponseType.OK) {
                this.Unsubscribe();
                this.client = null;
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Iterable<org.example.domain.Child> GetAllChildren() {
        try {
            Request request = networking.grpc.Request.newBuilder()
                    .setType(RequestType.GET_CHILDREN)
                    .build();
            Response response = this.blockingStub.getChildren(request);
            if (response.getType() == ResponseType.CHILDREN_RECEIVED) {
                List<Child> childrenList = new ArrayList<>();

                response.getEntitiesList().forEach(child -> {
                    org.example.domain.Child domainChild =
                            (org.example.domain.Child)
                            new org.example.domain.Child(
                            child.getChild().getName(),
                            child.getChild().getCnp()
                    ).SetId((int)child.getChild().getId());
                    childrenList.add(domainChild);
                });
                return childrenList;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public org.example.domain.Child GetChildById(int id) {
        return null;
    }

    @Override
    public org.example.domain.Child AddChild(String name, String cnp) {
        try {
            Request request = networking.grpc.Request.newBuilder()
                    .setType(RequestType.ADD_CHILD)
                    .setEntity(networking.grpc.Entity.newBuilder()
                            .setChild(
                                    networking.grpc.Child.newBuilder()
                                            .setName(name)
                                            .setCnp(cnp)
                                            .build())
                            .build())
                    .build();
            Response response = this.blockingStub.addChild(request);
            if (response.getType() == ResponseType.CHILD_ADDED) {
                return (Child) new org.example.domain.Child(
                        name,
                        cnp
                ).SetId((int)response.getEntity().getChild().getId());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<org.example.domain.Child> GetChildByAge(int age) {
        return null;
    }

    @Override
    public Iterable<org.example.domain.Event> GetAllEvents() {
        try {
            Request request = networking.grpc.Request.newBuilder()
                    .setType(RequestType.GET_EVENTS)
                    .build();
            Response response = this.blockingStub.getEvents(request);
            if (response.getType() == ResponseType.EVENTS_RECEIVED) {
                List<org.example.domain.Event> eventList = new ArrayList<>();

                response.getEntitiesList().forEach(event -> {
                    org.example.domain.Event domainEvent =
                            (org.example.domain.Event)
                                    new org.example.domain.Event(
                                            event.getEvent().getName(),
                                            (int)event.getEvent().getMinAge(),
                                            (int)event.getEvent().getMaxAge()
                                    ).SetId((int)event.getChild().getId());
                    eventList.add(domainEvent);
                });
                return eventList;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public org.example.domain.Event GetEventById(int id) {
        return null;
    }

    @Override
    public org.example.domain.Event AddEvent(String name, int minAge, int maxAge) {
        try {
            Request request = networking.grpc.Request.newBuilder()
                    .setType(RequestType.ADD_EVENT)
                    .setEntity(networking.grpc.Entity.newBuilder()
                            .setEvent(
                                    networking.grpc.Event.newBuilder()
                                            .setName(name)
                                            .setMinAge(minAge)
                                            .setMaxAge(maxAge)
                                            .build())
                            .build())
                    .build();
            Response response = this.blockingStub.addEvent(request);
            if (response.getType() == ResponseType.EVENT_ADDED) {
                return (Event) new org.example.domain.Event(
                        name,
                        minAge,
                        maxAge
                ).SetId((int)response.getEntity().getEvent().getId());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public org.example.domain.LoginInfo GetByUsername(String username) {
        try {
            Request request = Request.newBuilder()
                    .setType(RequestType.GET_LOGIN)
                    .setEntity(Entity.newBuilder()
                            .setLoginInfo(
                                    LoginInfo.newBuilder()
                                            .setUsername(username)
                                            .build()
                            )
                            .build())
                    .build();
            Response response = this.blockingStub.getLogin(request);
            if (response != null) {
                return (org.example.domain.LoginInfo)
                        new org.example.domain.LoginInfo(
                                response.getEntity().getLoginInfo().getUsername(),
                                response.getEntity().getLoginInfo().getPassword()
                        ).SetId((int)response.getEntity().getLoginInfo().getId());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<org.example.domain.Signup> GetAllSignups() {
        try {
            Request request = networking.grpc.Request.newBuilder()
                    .setType(RequestType.GET_SIGNUPS)
                    .build();
            Response response = this.blockingStub.getSignups(request);
            if (response.getType() == ResponseType.SIGNUPS_RECEIVED) {
                List<org.example.domain.Signup> signupList = new ArrayList<>();

                response.getEntitiesList().forEach(signup -> {
                    org.example.domain.Signup domainSignup =
                            (org.example.domain.Signup)
                            new org.example.domain.Signup (
                                    (org.example.domain.Child)
                                    new org.example.domain.Child (
                                            signup.getSignup().getChild().getName(),
                                            signup.getSignup().getChild().getCnp()
                                    ).SetId((int)signup.getSignup().getChild().getId()),
                                    (org.example.domain.Event)
                                    new org.example.domain.Event(
                                            signup.getSignup().getEvent().getName(),
                                            (int)signup.getSignup().getEvent().getMinAge(),
                                            (int)signup.getSignup().getEvent().getMaxAge()
                                    ).SetId((int)signup.getSignup().getEvent().getId())
                            ).SetId(new org.example.domain.Tuple((int)signup.getSignup().getChild().getId(),
                                    (int)signup.getSignup().getEvent().getId()));
                    signupList.add(domainSignup);
                });
                return signupList;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public org.example.domain.Signup GetSignupById(int childId, int eventId) {
        return null;
    }

    @Override
    public Iterable<org.example.domain.Signup> GetSignupByEvent(int eventId) {
        return null;
    }

    @Override
    public Iterable<org.example.domain.Signup> GetSignupByChild(int childId) {
        return null;
    }

    @Override
    public Iterable<org.example.domain.Signup> GetAllSignupsMapped(IServiceChild serviceChild, IServiceEvent serviceEvent) {
        return null;
    }

    @Override
    public org.example.domain.Signup DeleteSignup(int childId, int eventId) {
        return null;
    }

    @Override
    public IServiceChild getServiceChild() {
        return null;
    }

    @Override
    public IServiceEvent getServiceEvent() {
        return null;
    }

    @Override
    public IServiceSignup getServiceSignup() {
        return null;
    }

    @Override
    public IServiceLogin getServiceLogin() {
        return null;
    }

    @Override
    public org.example.domain.Signup AddSignup(org.example.domain.Child child, org.example.domain.Event event) {
        return null;
    }

    @Override
    public org.example.domain.Event UpdateEvent(org.example.domain.Event event) {
        return null;
    }

    @Override
    public org.example.domain.Child UpdateChild(org.example.domain.Child child) {
        return null;
    }

    private void Subscribe(int id) {
        Request request = Request.newBuilder()
                .setType(RequestType.SUBSCRIBE)
                .setEntity(Entity.newBuilder()
                        .setLoginInfo(
                                LoginInfo.newBuilder()
                                        .setId(id)
                                        .build()
                        ).build())
                .build();
        StreamObserver<Response> responseObserver = new StreamObserver<Response>() {
            @Override
            public void onNext(Response response) {
                if (response.getType() == ResponseType.CHILD_ADDED) {
                    client.childAdded(
                            (org.example.domain.Child)
                            new Child (
                                    response.getEntity().getChild().getName(),
                                    response.getEntity().getChild().getCnp()
                            ).SetId((int)response.getEntity().getChild().getId())
                    );
                }
                if (response.getType() == ResponseType.EVENT_ADDED) {
                    client.eventAdded(
                            (org.example.domain.Event)
                                    new Event (
                                            response.getEntity().getEvent().getName(),
                                            (int)response.getEntity().getEvent().getMinAge(),
                                            (int)response.getEntity().getEvent().getMaxAge()
                                    ).SetId((int)response.getEntity().getEvent().getId())
                    );
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error receiving notifications: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Subscription completed.");
            }
        };
        this.asyncStub.subscribe(request, responseObserver);
    }

    private void Unsubscribe() {
        Request request = networking.grpc.Request.newBuilder()
                .setType(RequestType.UNSUBSCRIBE)
                .build();

        StreamObserver<Response> responseObserver = new StreamObserver<Response>() {
            @Override
            public void onNext(Response response) {
                if (response.getType() == ResponseType.OK) {
                    System.out.println("Unsubscribed successfully.");
                } else {
                    System.out.println("Unexpected response while unsubscribing.");
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error unsubscribing: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Unsubscribe request completed.");
            }
        };
        this.asyncStub.unsubscribe(request, responseObserver);
    }
}
