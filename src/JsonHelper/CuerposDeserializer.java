//TODO ESTO NO ES NECESARIO IGUALMENTE.
package JsonHelper;

import java.io.IOException;
import java.util.LinkedList;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import core.Coordenada;
import core.entidad.CuerpoVibora;

//https://stackoverflow.com/questions/25185545/jackson-object-mapper-annotations-to-deserialize-a-inner-collection
public class CuerposDeserializer extends JsonDeserializer<LinkedList<CuerpoVibora>> {

	@Override
	public LinkedList<CuerpoVibora> deserialize(JsonParser arg0, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		 ObjectMapper mapper = new ObjectMapper();
	        JsonNode node = mapper.readTree(arg0);
	        //LinkedList<CuerpoVibora> cuerpos = mapper.convertValue(node.findValues("coordenada"), new TypeReference<LinkedList<CuerpoVibora>>() {});
	        LinkedList<CuerpoVibora> cuerpos = new LinkedList<>();
	        
	        node.findValues("coordenada").forEach(item -> {
	            Coordenada p = getCoordenadaFromNode(item);
	            CuerpoVibora c = new CuerpoVibora(p, false);
	            cuerpos.add(c);
	        });
	       //Debug
	        /*try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	        return cuerpos;
	}
	
	//https://stackoverflow.com/questions/14702918/jackson-read-value-as-string
	//https://stackoverflow.com/questions/20832015/how-do-i-iterate-over-a-json-response-using-jackson-api-of-a-list-inside-a-list
	public static Coordenada getCoordenadaFromNode(JsonNode node) {
	    Coordenada c = new Coordenada();
	    c.setX(node.get("x").asInt());
	    c.setY(node.get("y").asInt());
	    return c;
	}
}