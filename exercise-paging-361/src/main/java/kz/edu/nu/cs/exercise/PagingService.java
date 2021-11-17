package kz.edu.nu.cs.exercise;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

@Path("/items")
public class PagingService {

    private List<String> list = new CopyOnWriteArrayList<String>();

    public PagingService() {
        for (int i = 0; i < 100; i++) {
            list.add("Entry " + String.valueOf(i));
        }
    }

    @GET
    public Response getMyList(@QueryParam("size") int size) {
        Gson gson = new Gson();
        String json;
        
        PagedHelper p = new PagedHelper();
        p.setList(list.subList((size-1)*10, size*10));

        if (size == 1) {
            p.setPrev(0);
        } else {
            p.setPrev(size-1);
        }

        if (size*10>=list.size()) {
            p.setNext(0);
        } else {
            p.setNext(size+1);
        }
        
        json = gson.toJson(p, PagedHelper.class);
        
        return Response.ok(json).build();
    }
}
