package org.example.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.protocol.Worker;
import org.example.services.IService;

import java.net.Socket;

public class ConcurrentServer extends AbsConcurrentServer {
    private IService Server;
    private static Logger logger = LogManager.getLogger(ConcurrentServer.class);

    public ConcurrentServer(int port, IService Server) {
        super(port);
        this.Server = Server;
        logger.info("ConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        Worker worker=new Worker(Server, client);
        Thread tw=new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        logger.info("Stopping services ...");
    }
}

