// package com.analia.web.rs.media;
//
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.services.youtube.YouTube;
//import com.google.api.services.youtube.YouTubeRequestInitializer;
//import com.google.api.services.youtube.model.PlaylistItem;
//import com.google.api.services.youtube.model.PlaylistItemListResponse;
//
//import io.vertx.core.json.jackson.JacksonFactory;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import jakarta.persistence.Entity;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.Query;
//import jakarta.persistence.Table;
//import jakarta.ws.rs.Consumes;
//import jakarta.ws.rs.POST;
//import jakarta.ws.rs.Path;
//import jakarta.ws.rs.Produces;
//import jakarta.ws.rs.core.Response;
//
//import java.util.List;
//import java.util.Map;
//
//
//@Path("/video")
//@ApplicationScoped
//public class VideoResource {
//
//    public static final String API_KEY = "AIzaSyCNsrpHsp6722TRubmJKD87xfbBWiufyTo";
//
//    public static final String PLAY_LIST_ME_TE_TE_GOL_ID = "PLmYee9khYWhNY6Z9yQ6BfT5RPzvxkbsrG";
//
//    private YouTube youTube;
//
//    public VideoResource() {
//        try{
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//
//    @POST
//    @Path("/play.s")
//    @Consumes("application/json")
//    @Produces("application/json")
//    public Response play(Map<String, Object> input) throws Exception {
//
//        return Response.ok().build();
//    }
//}
