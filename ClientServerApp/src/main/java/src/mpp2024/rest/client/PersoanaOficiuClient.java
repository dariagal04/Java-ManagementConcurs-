//package src.mpp2024.rest.client;
//
//import org.springframework.http.HttpRequest;
//import org.springframework.http.MediaType;
//import org.springframework.http.client.ClientHttpRequestExecution;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.http.client.ClientHttpResponse;
//import org.springframework.web.client.RestClient;
//import ro.mpp2024.model.Event;
//import ro.mpp2024.services.rest.ServiceException;
//
//import java.io.IOException;
//import java.util.concurrent.Callable;
//
//public class PersoanaOficiuClient {
//    RestClient restClient = RestClient.builder()
//            .requestInterceptor(new CustomRestClientInterceptor())
//            .build();
//
//
//    public static final String URL = "http://localhost:8080/concurs/events";
//
//    private <T> T execute(Callable<T> callable) {
//        try{
//            return callable.call();
//        } catch (Exception e) {
//            throw new ServiceException(e);
//        }
//    }
//
//    public PersoanaOficiu[] getAll(){
//        return execute(()->restClient.get().uri(URL).retrieve().body(Event[].class));
//    }
//
//    public PersoanaOficiu getById(Integer id){
//        return execute(()->restClient.get().uri(URL + "/" + id).retrieve().body(Event.class));
//    }
//
//    public PersoanaOficiu create(PersoanaOficiu event){
//        return execute(()->restClient.post().uri(URL).contentType(MediaType.APPLICATION_JSON).body(event).retrieve().body(Event.class));
//    }
//
//    public void delete(Integer id){
//        execute(() -> restClient.delete().uri(URL+ "/" + id).retrieve().toBodilessEntity());
//    }
//
//    public PersoanaOficiu update(Integer currentId, Event event){
//        return execute(() -> restClient.put()
//                .uri(URL + "/" + currentId)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(event)
//                .retrieve()
//                .body(PersoanaOficiu.class));
//    }
//
//    public static class CustomRestClientInterceptor implements ClientHttpRequestInterceptor {
//
//        @Override
//        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
//            System.out.println("Sending a "+request.getMethod()+" request to "+request.getURI()+" and body ["+new String(body)+"]");
//            ClientHttpResponse response = null;
//            try{
//                response = execution.execute(request, body);
//                System.out.println("Got response code "+ response.getStatusCode());
//            }catch(IOException e){
//                System.err.println("Eroare executie: "+e);
//            }
//            return response;
//        }
//    }
//}
