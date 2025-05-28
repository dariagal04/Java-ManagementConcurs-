package src.mpp2024;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.mpp2024.domain.Participant;
import src.mpp2024.domain.PersoanaOficiu;
import src.mpp2024.service.*;
import src.mpp2024.services.IConcursService;

import src.mpp2024.repo.Interfete.*;
import src.mpp2024.repo.DB.*;
import src.mpp2024.utils.AbstractServer;
import src.mpp2024.utils.ConcursObjectConcurrentServer;

import java.io.File;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.Properties;

public class StartObjectServer {
    private static int defaultPort=55556;
    private static Logger logger= LogManager.getLogger(StartObjectServer.class);

    public static void main(String[] args) {
        Properties prop = new Properties();
        try{
            prop.load(StartObjectServer.class.getClassLoader().getResourceAsStream("server.properties"));
            logger.debug("Server properties loaded");
        }catch (IOException e){
            logger.error("Cannot find server.properties "+e.getMessage());
            logger.debug("Looking into directory {} ", (new File(".")).getAbsolutePath());
            return;
        }

        ICategorieVarstaRepo categorieVarsta = new CategorieDBRepo(prop);
        IInscriereRepo inscriere = new InscriereDBRepo(prop);
        INumeProbaRepo numeProba = new NumeProbaDBRepo(prop);
        IParticipantRepo participantRepo = new ParticipantiDBRepo(prop);
        IPersoanaOficiuRepo persoanaOficiu = new PersoanaOficiuDBRepo(prop);

        IPersoanaOficiuRepo persoanaOficiuHRepo = new PersoanaOficiuHibernateDBRepo();
        IParticipantRepo participantHRepo = new ParticipantHibernateDBRepo();

        CategorieService concursServiceCategorie=new CategorieService((CategorieDBRepo) categorieVarsta);
        InscriereService concursServiceInscriere= new InscriereService((InscriereDBRepo) inscriere);
        NumeProbaService concursServiceNumeProba = new NumeProbaService((NumeProbaDBRepo) numeProba);
        ParticipantiService concursServiceParticipant = new ParticipantiService(participantHRepo);
        PersoanaOficiuService concursServicePersoanaOficiu = new PersoanaOficiuService( persoanaOficiuHRepo);
        IConcursService service = new Services(concursServiceCategorie,concursServiceInscriere,concursServiceNumeProba,concursServiceParticipant,concursServicePersoanaOficiu);
        int serverPort=defaultPort;
        try{
            serverPort=Integer.parseInt(prop.getProperty("server.port"));
        }catch (NumberFormatException e){
            logger.error("Wrong port number "+e.getMessage());
            logger.debug("Using default port "+defaultPort);
        }
        logger.debug("Starting server on port "+serverPort);
        AbstractServer server=new ConcursObjectConcurrentServer(serverPort,service);

        try{
            server.start();
        }catch(ServerException e){
            logger.error("Error starting the server "+e.getMessage());
        }
    }
}
