package Data;

import Entities.Client;
import Entities.Gaurantors;
import Entities.IndividualDetail;
import Entities.Work;

import java.util.HashMap;

public class Clients {
    public HashMap<String, HashMap<String, String>> clients = new HashMap<>();

    public void Individual() {
        String id = new Client().create().get("id");
        IndividualDetail individualDetail = new IndividualDetail();
        individualDetail.getDetails().put("client_id", id);
        Work work = new Work();
        work.getDetails().put("client_id", id);
        work.create();
        String gaurantorsID = new Gaurantors().create().get("id");
//        new ClientGaurantors().
    }

    public void SoleProprietor() {

    }

    public void Corporate() {

    }

    public void enter() {

    }
}
