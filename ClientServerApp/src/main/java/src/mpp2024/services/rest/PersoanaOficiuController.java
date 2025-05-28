//package src.mpp2024.services.rest;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import src.mpp2024.domain.PersoanaOficiu;
//import src.mpp2024.domain.validators.ValidationException;
//import src.mpp2024.repo.DB.PersoanaOficiuHibernateDBRepo;
//import src.mpp2024.repo.DB.PersoanaOficiuDBRepo;
//import src.mpp2024.services.ConcursException;
//
//import java.util.Objects;
//
//@CrossOrigin(origins = "http://localhost:5173")
//@RestController
//@RequestMapping("/concurs/events")
//public class PersoanaOficiuController {
//    @Autowired
//    private PersoanaOficiuDBRepo eventRepository;
//
//    @RequestMapping(method= RequestMethod.GET)
//    public ResponseEntity<?> getAll(){
//        try{
//            System.out.println("Get al events....");
//            eventRepository.getAll();
//            return new ResponseEntity<>(eventRepository.getAll(), HttpStatus.OK);
//        }catch(ConcursException e){
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//
//
//    }
//
//    @RequestMapping(value="/{id}", method = RequestMethod.GET)
//    public ResponseEntity<?> getById(@PathVariable Integer id){
//        System.out.println("Get by id "+id);
//        PersoanaOficiu event = eventRepository.getOne(id);
//        if(event == null){
//            return new ResponseEntity<>("Event not found", HttpStatus.NOT_FOUND);
//        }else{
//            return new ResponseEntity<>(event, HttpStatus.OK);
//        }
//    }
//
//    @RequestMapping(method = RequestMethod.POST)
//    public PersoanaOficiu create(@RequestBody PersoanaOficiu newEvent){
//        System.out.println("Create new event");
//        return eventRepository.saveEntity(newEvent);
//    }
//
//    @RequestMapping(value ="/{id}", method = RequestMethod.PUT)
//    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody PersoanaOficiu newEvent){
//        System.out.println("Update event");
//        if(!Objects.equals(newEvent.getId(), id)){
//            return new ResponseEntity<>("Invalid id",HttpStatus.BAD_REQUEST);
//        }
//        eventRepository.updateEntity(id, newEvent);
//        //newEvent.setId(id);
//        return new ResponseEntity<>(newEvent, HttpStatus.OK);
//    }
//
//    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
//    public ResponseEntity<?> delete(@PathVariable Integer id){
//        System.out.println("Delete event");
//        ResponseEntity<?> event = getById(id);
//        if(event == null){
//            return new ResponseEntity<>("Event not found", HttpStatus.BAD_REQUEST);
//        }
//        try{
//            eventRepository.deleteEntity(id);
//            return new ResponseEntity<>("Event deleted", HttpStatus.OK);
//        }catch (ConcursException ex){
//            System.out.println(ex.getMessage());
//            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @RequestMapping("/{id}/name")
//    public String name(@PathVariable Integer id){
//        PersoanaOficiu event = eventRepository.getOne(id);
//        System.out.println("Event..."+event);
//
//        return event.getUsername();
//    }
//
//    @ExceptionHandler(ConcursException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public String eventError(ConcursException ex){
//        return ex.getMessage();
//    }
//}
